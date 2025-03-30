package com.onixbyte.clearledger.data.request;

/**
 * A data transfer object for creating a new ledger.
 * <p>
 * This record encapsulates the request data required to create a ledger, including its name and
 * description.
 *
 * @param name        the name of the ledger to be created
 * @param description the description of the ledger to be created
 * @author zihluwang
 */
public record CreateLedgerRequest(
        String name,
        String description
) {
}