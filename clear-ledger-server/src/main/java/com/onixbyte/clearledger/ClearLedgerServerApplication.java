package com.onixbyte.clearledger;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Clear Ledger Server application.
 *
 * @author zihluwang
 */
@Slf4j
@MapperScan("com.onixbyte.clearledger.repository")
@SpringBootApplication
public class ClearLedgerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClearLedgerServerApplication.class, args);
    }

}
