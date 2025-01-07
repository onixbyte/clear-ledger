package com.onixbyte.clearledger.data.view;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LedgerView(
        String id,
        String name,
        String description
) {
}
