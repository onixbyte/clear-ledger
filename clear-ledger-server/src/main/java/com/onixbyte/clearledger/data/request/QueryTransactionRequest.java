package com.onixbyte.clearledger.data.request;

import java.time.LocalDateTime;

public record QueryTransactionRequest(
        LocalDateTime transactionDateStart,
        LocalDateTime transactionDateEnd
) {
}
