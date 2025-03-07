package com.onixbyte.clearledger.data.request;

import java.time.LocalDateTime;

public record CreateTransactionRequest(
        String ledgerId,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {
}
