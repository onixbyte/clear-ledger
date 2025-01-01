package com.onixbyte.clearledger.data.biz;

import java.time.LocalDateTime;

/**
 * Represents the ledger data and the user-related information.
 *
 * @param id          the unique identifier of the ledger entry
 * @param name        the name of the ledger entry
 * @param description a brief description of the ledger entry
 * @param createdBy   the identifier of the user who created the ledger entry
 * @param createdAt   the timestamp when the ledger entry was created
 * @param role        the role of the user in relation to the ledger entry
 * @param joinedAt    the timestamp when the user joined the ledger entry
 * @author zihluwang
 */
public record LedgerBizData(
        Long id,
        String name,
        String description,
        Long createdBy,
        LocalDateTime createdAt,
        String role,
        LocalDateTime joinedAt
) {
}
