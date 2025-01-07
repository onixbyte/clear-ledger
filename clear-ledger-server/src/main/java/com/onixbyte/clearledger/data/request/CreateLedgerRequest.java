package com.onixbyte.clearledger.data.request;

public record CreateLedgerRequest(
        String name,
        String description
) {
}
