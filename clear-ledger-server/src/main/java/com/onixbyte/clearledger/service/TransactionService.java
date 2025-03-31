package com.onixbyte.clearledger.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Service class for managing transactions within ledgers.
 * <p>
 * Provides methods to create, update, and query transactions, ensuring proper validation and
 * integration with ledger and user permissions, with a scheduled task to reset serial numbers daily.
 *
 * @author zihluwang
 * @author siujamo
 */
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

    /**
     * Constructs a transaction service with required dependencies.
     *
     * @param transactionRepository    the repository for transaction data operations
     * @param transactionIdCreator     the creator for generating unique transaction identifiers
     * @param ledgerRepository         the repository for ledger data operations
     * @param userLedgerRepository     the repository for user-ledger relationship data operations
     * @param serialService            the service for managing serial numbers
     * @param viewTransactionRepository the repository for view transaction data operations
     */
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

    /**
     * Validates prerequisites for transaction operations.
     * <p>
     * Ensures the specified ledger exists and the user has permission to perform transactions on it.
     *
     * @param userId   the ID of the user performing the operation
     * @param ledgerId the ID of the ledger to validate
     * @throws BizException if the ledger does not exist or the user lacks permission
     */
    private void preValidate(String userId, String ledgerId) {
        if (!ledgerRepository.hasLedger(ledgerId)) {
            throw BizException.notFound("账本不存在");
        }

        if (!userLedgerRepository.canWriteTransaction(userId, ledgerId)) {
            throw BizException.forbidden("您没有对该账本的操作权限");
        }
    }

    /**
     * Retrieves a paginated list of transactions for a specified ledger.
     * <p>
     * Queries transactions with optional date range filtering, sorted by transaction date in
     * descending order.
     *
     * @param ledgerId the ID of the ledger to query transactions for
     * @param pageNum  the page number to retrieve (1-based)
     * @param pageSize the number of transactions per page
     * @param request  the optional {@link QueryTransactionRequest} with date range filters
     * @return a {@link Page} of {@link ViewTransaction} objects
     */
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

    /**
     * Creates a new transaction in the specified ledger.
     * <p>
     * Validates the user's permission, generates a unique ID, and persists the transaction, returning
     * it as a view object.
     *
     * @param request the {@link CreateTransactionRequest} containing transaction details
     * @return the created {@link ViewTransaction}
     * @throws BizException if validation fails
     */
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

    /**
     * Updates an existing transaction in the specified ledger.
     * <p>
     * Validates the user's permission and updates the transaction, ignoring null values, then returns
     * it as a view object.
     *
     * @param request the {@link UpdateTransactionRequest} containing updated transaction details
     * @return the updated {@link ViewTransaction}
     * @throws BizException if validation fails
     */
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

    /**
     * Resets the serial number for transaction-related operations daily.
     * <p>
     * Scheduled to run at midnight every day to reset the transaction serial counter.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetSerial() {
        log.info("Resetting transaction serial.");
        serialService.resetSerial(cacheTag);
        log.info("Transaction serial has been reset to 0.");
    }
}
