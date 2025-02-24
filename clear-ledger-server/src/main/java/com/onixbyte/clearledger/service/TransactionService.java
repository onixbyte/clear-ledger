package com.onixbyte.clearledger.service;

import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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

}
