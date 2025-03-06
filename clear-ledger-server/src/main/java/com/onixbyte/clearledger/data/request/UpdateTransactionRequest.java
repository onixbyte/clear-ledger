package com.onixbyte.clearledger.data.request;

public record UpdateTransactionRequest(
        String id,
        String ledgerId,
        Integer amount,
        String description,
        String transactionDate
) {
}
