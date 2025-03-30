package com.onixbyte.clearledger.generator;

import com.onixbyte.clearledger.constant.IdType;
import com.onixbyte.clearledger.service.SerialService;
import com.onixbyte.clearledger.common.Formatters;
import com.onixbyte.guid.GuidCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * A generator for creating unique transaction identifiers.
 * <p>
 * This class implements the {@link GuidCreator} interface to produce unique string identifiers
 * for transactions, combining a type code, a formatted date, and a serial number.
 *
 * @author zihluwang
 */
@Component
public class TransactionIdCreator implements GuidCreator<String> {

    private final SerialService serialService;

    /**
     * Constructs a transaction identifier creator with the required serial service.
     * <p>
     * Initialises the creator with a dependency on {@link SerialService} to generate sequential
     * numbers for transaction IDs.
     *
     * @param serialService the service used to generate serial numbers
     */
    @Autowired
    public TransactionIdCreator(SerialService serialService) {
        this.serialService = serialService;
    }

    /**
     * Generates the next unique transaction identifier.
     * <p>
     * Combines the transaction type code, the current date formatted using
     * {@link Formatters#SHORTENED_DATE_FORMATTER}, and a four-digit serial number padded with
     * leading zeros.
     *
     * @return a unique string identifier for a transaction
     */
    @Override
    public String nextId() {
        var date = LocalDate.now().format(Formatters.SHORTENED_DATE_FORMATTER);
        return "%s%s%04d".formatted(IdType.TRANSACTION.getCode(), date, serialService.nextSerial("tx"));
    }
}