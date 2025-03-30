package com.onixbyte.clearledger.data.request;

import java.time.LocalDateTime;

/**
 * A data transfer object for updating an existing transaction.
 * <p>
 * This record encapsulates the request data required to update a transaction, including its
 * identifier, ledger identifier, amount, description, and transaction date.
 *
 * @param id              the identifier of the transaction to update
 * @param ledgerId        the identifier of the ledger associated with the transaction
 * @param amount          the updated amount of the transaction
 * @param description     the updated description of the transaction
 * @param transactionDate the updated date and time of the transaction
 * @author zihluwang
 */
public record UpdateTransactionRequest(
        String id,
        String ledgerId,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {
}
