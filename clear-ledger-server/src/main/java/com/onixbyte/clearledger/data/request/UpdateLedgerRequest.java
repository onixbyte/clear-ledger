package com.onixbyte.clearledger.data.request;

/**
 * A data transfer object for updating an existing ledger.
 * <p>
 * This record encapsulates the request data required to update a ledger, including its identifier,
 * name, and description.
 *
 * @param id the identifier of the ledger to update
 * @param name the updated name of the ledger
 * @param description the updated description of the ledger
 * @author zihluwang
 */
public record UpdateLedgerRequest(
        String id,
        String name,
        String description
) {
}
