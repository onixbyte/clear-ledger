package com.onixbyte.clearledger.data.biz;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BizLedger(
        Long id,
        String name,
        String description,
        String role,
        LocalDateTime joinedAt
) {
}
