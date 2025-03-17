package com.onixbyte.clearledger.data.request;

import java.time.LocalDate;

public record QueryTransactionRequest(
        LocalDate transactionDateStart,
        LocalDate transactionDateEnd
) {
}
