package com.onixbyte.clearledger.data.request;

import java.time.LocalDate;

/**
 * A data transfer object for querying transactions.
 * <p>
 * This record encapsulates the request data required to query transactions, specifying a date range
 * for filtering.
 *
 * @param transactionDateStart the start date of the transaction date range (inclusive)
 * @param transactionDateEnd the end date of the transaction date range (inclusive)
 * @author zihluwang
 */
public record QueryTransactionRequest(
        LocalDate transactionDateStart,
        LocalDate transactionDateEnd
) {
}