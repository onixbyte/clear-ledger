package com.onixbyte.clearledger.data.request;

import java.time.LocalDateTime;

public record UpdateTransactionRequest(
        String id,
        String ledgerId,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {
}
