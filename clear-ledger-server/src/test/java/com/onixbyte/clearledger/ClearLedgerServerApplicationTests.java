package com.onixbyte.clearledger;

import com.onixbyte.clearledger.repository.LedgerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@ActiveProfiles({"db"})
class ClearLedgerServerApplicationTests {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Test
    @Transactional
    void contextLoads() {
        var ledgers = ledgerRepository.findLedgersByUserId(1L);
        log.info("{}", ledgers);
    }

}
