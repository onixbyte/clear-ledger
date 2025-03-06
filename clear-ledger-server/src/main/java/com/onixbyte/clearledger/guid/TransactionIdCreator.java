package com.onixbyte.clearledger.guid;

import com.onixbyte.clearledger.constant.IdType;
import com.onixbyte.clearledger.service.SerialService;
import com.onixbyte.guid.GuidCreator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionIdCreator implements GuidCreator<String> {

    private final DateTimeFormatter dateFormatter;
    private final SerialService serialService;

    public TransactionIdCreator(DateTimeFormatter dateFormatter, SerialService serialService) {
        this.dateFormatter = dateFormatter;
        this.serialService = serialService;
    }

    @Override
    public String nextId() {
        var date = LocalDate.now().format(dateFormatter);
        return "%s%s%02d".formatted(IdType.TRANSACTION.getCode(), date, serialService.nextSerial("tx"));
    }
}
