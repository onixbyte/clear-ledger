package com.onixbyte.clearledger.data.request;

public record UpdateLedgerRequest(
        Long id,
        String name,
        String description
) {
}
