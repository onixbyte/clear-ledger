package com.onixbyte.clearledger.service;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getTransactionPage(Long ledgerId, Integer pageNum, Integer pageSize) {
        return transactionRepository.paginate(pageNum, pageSize, TransactionTableDef.TRANSACTION.LEDGER_ID.eq(ledgerId));
    }

}
