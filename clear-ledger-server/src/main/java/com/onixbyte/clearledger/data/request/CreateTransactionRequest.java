package com.onixbyte.clearledger.data.request;

import java.time.LocalDateTime;

/**
 * A data transfer object for creating a new transaction.
 * <p>
 * This record encapsulates the request data required to create a transaction, including the ledger
 * identifier, amount, description, and transaction date.
 *
 * @param ledgerId        the identifier of the ledger associated with the transaction
 * @param amount          the amount of the transaction
 * @param description     the description of the transaction
 * @param transactionDate the date and time the transaction occurred.
 * @author zihluwang
 */
public record CreateTransactionRequest(
        String ledgerId,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {
}