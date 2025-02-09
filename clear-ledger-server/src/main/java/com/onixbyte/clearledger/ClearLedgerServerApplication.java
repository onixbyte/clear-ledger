package com.onixbyte.clearledger;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Clear Ledger Server application.
 *
 * @author zihluwang
 */
@MapperScan("com.onixbyte.clearledger.repository")
@SpringBootApplication
public class ClearLedgerServerApplication {

    private static final Logger log = LoggerFactory.getLogger(ClearLedgerServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClearLedgerServerApplication.class, args);
    }

}
