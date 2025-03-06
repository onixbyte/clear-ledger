package com.onixbyte.clearledger.data.request;

public record CreateTransactionRequest(
        String ledgerId,
        Integer amount,
        String description,
        String transactionDate
) {
}
