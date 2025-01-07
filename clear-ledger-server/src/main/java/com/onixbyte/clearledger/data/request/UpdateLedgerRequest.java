package com.onixbyte.clearledger.data.request;

import lombok.Builder;

@Builder
public record UpdateLedgerRequest(
        Long id,
        String name,
        String description
) {
}
