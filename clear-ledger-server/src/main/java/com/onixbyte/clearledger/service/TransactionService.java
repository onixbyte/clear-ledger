package com.onixbyte.clearledger.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.onixbyte.clearledger.common.Formatters;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.data.entity.table.ViewTransactionTableDef;
import com.onixbyte.clearledger.data.request.CreateTransactionRequest;
import com.onixbyte.clearledger.data.request.QueryTransactionRequest;
import com.onixbyte.clearledger.data.request.UpdateTransactionRequest;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.repository.ViewTransactionRepository;
import com.onixbyte.clearledger.security.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.TransactionRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import com.onixbyte.guid.GuidCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final String cacheTag = "tx";

    private final TransactionRepository transactionRepository;
    private final GuidCreator<String> transactionIdCreator;
    private final LedgerRepository ledgerRepository;
    private final UserLedgerRepository userLedgerRepository;
    private final SerialService serialService;
    private final ViewTransactionRepository viewTransactionRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              GuidCreator<String> transactionIdCreator,
                              LedgerRepository ledgerRepository,
                              UserLedgerRepository userLedgerRepository,
                              SerialService serialService, ViewTransactionRepository viewTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionIdCreator = transactionIdCreator;
        this.ledgerRepository = ledgerRepository;
        this.userLedgerRepository = userLedgerRepository;
        this.serialService = serialService;
        this.viewTransactionRepository = viewTransactionRepository;
    }

    private void preValidate(String userId, String ledgerId) {
        if (!ledgerRepository.hasLedger(ledgerId)) {
            throw new BizException(HttpStatus.NOT_FOUND, "账本不存在");
        }

        if (!userLedgerRepository.canWriteTransaction(userId, ledgerId)) {
            throw new BizException(HttpStatus.FORBIDDEN, "您没有对该账本的操作权限");
        }
    }

    public Page<ViewTransaction> getTransactionPage(String ledgerId,
                                                    Long pageNum,
                                                    Long pageSize,
                                                    QueryTransactionRequest request) {
        final var table = ViewTransactionTableDef.VIEW_TRANSACTION;
        var queryWrapper = QueryWrapper.create()
                .where(table.LEDGER_ID.eq(ledgerId))
                .orderBy(table.TRANSACTION_DATE, false);
        if (Objects.nonNull(request)) {
            if (Objects.nonNull(request.transactionDateStart()) && Objects.nonNull(request.transactionDateEnd())) {
                queryWrapper.and(table.TRANSACTION_DATE.between(request.transactionDateStart().atTime(0, 0, 0), request.transactionDateEnd().atTime(23, 59, 59)));
            }
        }
        return viewTransactionRepository.paginate(new Page<>(pageNum, pageSize), queryWrapper);
    }

    public ViewTransaction createTransaction(CreateTransactionRequest request) {
        var currentUser = UserHolder.getCurrentUser();
        preValidate(currentUser.id(), request.ledgerId());

        var transaction = Transaction.builder()
                .id(transactionIdCreator.nextId())
                .ledgerId(request.ledgerId())
                .userId(currentUser.id())
                .amount(request.amount())
                .transactionDate(request.transactionDate())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();
        transactionRepository.insert(transaction);
        return transaction.toView(currentUser.username());
    }

    public ViewTransaction updateTransaction(UpdateTransactionRequest request) {
        var currentUser = UserHolder.getCurrentUser();
        preValidate(currentUser.id(), request.ledgerId());

        var transaction = Transaction.builder()
                .id(request.id())
                .ledgerId(request.ledgerId())
                .userId(currentUser.id())
                .amount(request.amount())
                .transactionDate(request.transactionDate())
                .description(request.description())
                .build();
        // the default behaviour of `update(T)` method from `BaseMapper` will ignore null values
        transactionRepository.update(transaction);
        return transaction.toView(currentUser.username());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetSerial() {
        log.info("Resetting transaction serial.");
        serialService.resetSerial(cacheTag);
        log.info("Transaction serial has been reset to 0.");
    }
}
