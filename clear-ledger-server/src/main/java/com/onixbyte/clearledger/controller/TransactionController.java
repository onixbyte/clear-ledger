package com.onixbyte.clearledger.controller;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.Transaction;
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
    public Page<TransactionResponse> getTransactions(@PathVariable Long ledgerId,
                                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return transactionService.getTransactionPage(ledgerId, pageNum, pageSize)
                .map(Transaction::toView);
    }
}
