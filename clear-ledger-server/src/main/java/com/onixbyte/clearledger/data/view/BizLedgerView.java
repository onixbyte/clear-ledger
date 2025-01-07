package com.onixbyte.clearledger.data.view;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BizLedgerView(
        String id,
        String name,
        String description,
        String role,
        LocalDateTime joinedAt
) {
}
