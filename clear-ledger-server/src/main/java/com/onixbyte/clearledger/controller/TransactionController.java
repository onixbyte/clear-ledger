package com.onixbyte.clearledger.controller;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import com.onixbyte.clearledger.data.request.CreateTransactionRequest;
import com.onixbyte.clearledger.data.request.QueryTransactionRequest;
import com.onixbyte.clearledger.data.request.UpdateTransactionRequest;
import com.onixbyte.clearledger.data.response.TransactionResponse;
import com.onixbyte.clearledger.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing transaction operations.
 * <p>
 * This class provides REST endpoints for creating, updating, and querying transactions within
 * the application. It integrates with {@link TransactionService} to handle transaction-related
 * business logic.
 *
 * @author zihluwang
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    /**
     * Constructs a transaction controller with the required service.
     *
     * @param transactionService the service handling transaction operations
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieves a paginated list of transactions for a ledger.
     * <p>
     * This endpoint returns a page of transactions associated with the specified ledger, filtered
     * by the provided query parameters.
     *
     * @param ledgerId the ID of the ledger to query transactions from
     * @param pageNum the page number (default is 1)
     * @param pageSize the number of transactions per page (default is 10)
     * @param request the query request containing additional filters
     * @return a {@link Page} of {@link TransactionResponse} objects
     */
    @GetMapping("/{ledgerId:\\d+}")
    public Page<TransactionResponse> getTransactions(@PathVariable String ledgerId,
                                                     @RequestParam(required = false, defaultValue = "1") Long pageNum,
                                                     @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                     @ModelAttribute QueryTransactionRequest request) {
        return transactionService.getTransactionPage(ledgerId, pageNum, pageSize, request)
                .map(ViewTransaction::toResponse);
    }

    /**
     * Creates a new transaction.
     * <p>
     * This endpoint creates a transaction based on the provided request and returns its details.
     *
     * @param request the request containing transaction details
     * @return the created {@link TransactionResponse}
     */
    @PostMapping
    public TransactionResponse createTransaction(@RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request)
                .toResponse();
    }

    /**
     * Updates an existing transaction.
     * <p>
     * This endpoint updates a transaction based on the provided request and returns its updated details.
     *
     * @param request the request containing updated transaction details
     * @return the updated {@link TransactionResponse}
     */
    @PatchMapping
    public TransactionResponse updateTransaction(@RequestBody UpdateTransactionRequest request) {
        return transactionService.updateTransaction(request)
                .toResponse();
    }
}