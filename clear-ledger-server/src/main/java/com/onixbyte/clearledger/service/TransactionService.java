package com.onixbyte.clearledger.service;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.data.request.CreateTransactionRequest;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.holder.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.TransactionRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import com.onixbyte.guid.GuidCreator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final GuidCreator<String> transactionIdCreator;
    private final DateTimeFormatter datetimeFormatter;
    private final LedgerRepository ledgerRepository;
    private final UserLedgerRepository userLedgerRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              GuidCreator<String> transactionIdCreator,
                              DateTimeFormatter datetimeFormatter,
                              LedgerRepository ledgerRepository, UserLedgerRepository userLedgerRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionIdCreator = transactionIdCreator;
        this.datetimeFormatter = datetimeFormatter;
        this.ledgerRepository = ledgerRepository;
        this.userLedgerRepository = userLedgerRepository;
    }

    public Page<ViewTransaction> getTransactionPage(Long ledgerId, Long pageNum, Long size) {
        var offset = (pageNum - 1) * size;
        var transactions = transactionRepository.selectPaginateViewTransactions(ledgerId, offset, size);

        var result = new Page<ViewTransaction>(pageNum, size);
        result.setRecords(transactions);

        var count = transactionRepository.selectCountByCondition(TransactionTableDef.TRANSACTION
                .LEDGER_ID.eq(ledgerId));
        result.setTotalRow(count);

        return result;
    }

    @Transactional
    public ViewTransaction createTransaction(CreateTransactionRequest request) {
        var currentUser = UserHolder.getCurrentUser();

        if (!ledgerRepository.hasLedger(request.ledgerId())) {
            throw new BizException(HttpStatus.NOT_FOUND, "账本不存在");
        }

        if (!userLedgerRepository.canWriteTransaction(currentUser.id(), request.ledgerId())) {
            throw new BizException(HttpStatus.FORBIDDEN, "您没有对该账本的操作权限");
        }

        var transaction = Transaction.builder()
                .id(transactionIdCreator.nextId())
                .ledgerId(request.ledgerId())
                .userId(currentUser.id())
                .transactionDate(LocalDateTime.parse(request.transactionDate(), datetimeFormatter))
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();
        transactionRepository.insert(transaction);
        return transaction.toView(currentUser.username());
    }

}
