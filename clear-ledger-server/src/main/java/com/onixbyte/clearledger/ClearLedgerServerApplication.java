package com.onixbyte.clearledger;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Clear Ledger Server application.
 * <p>
 * This class initialises and starts the Spring Boot application, enabling autoconfiguration and
 * scanning for MyBatis mappers in the specified repository package.
 *
 * @author zihluwang
 */
@MapperScan("com.onixbyte.clearledger.repository")
@SpringBootApplication
public class ClearLedgerServerApplication {

    private static final Logger log = LoggerFactory.getLogger(ClearLedgerServerApplication.class);

    /**
     * Launches the Clear Ledger Server application.
     * <p>
     * Invokes the Spring Boot application runner with the provided command-line arguments.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ClearLedgerServerApplication.class, args);
    }

}
