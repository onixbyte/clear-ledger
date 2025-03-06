package com.onixbyte.clearledger.data.request;

public record UpdateLedgerRequest(
        String id,
        String name,
        String description
) {
}
