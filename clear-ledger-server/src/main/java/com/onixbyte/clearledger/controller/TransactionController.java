package com.onixbyte.clearledger.controller;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import com.onixbyte.clearledger.data.request.CreateTransactionRequest;
import com.onixbyte.clearledger.data.request.UpdateTransactionRequest;
import com.onixbyte.clearledger.data.response.TransactionResponse;
import com.onixbyte.clearledger.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Transaction Controller.
 *
 * @author zihluwang
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{ledgerId:\\d+}")
    public Page<TransactionResponse> getTransactions(@PathVariable String ledgerId,
                                                     @RequestParam(required = false, defaultValue = "1") Long pageNum,
                                                     @RequestParam(required = false, defaultValue = "10") Long pageSize) {
        return transactionService.getTransactionPage(ledgerId, pageNum, pageSize)
                .map(ViewTransaction::toResponse);
    }

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request)
                .toResponse();
    }

    @PatchMapping
    public TransactionResponse updateTransaction(@RequestBody UpdateTransactionRequest request) {
        return transactionService.updateTransaction(request)
                .toResponse();
    }
}
