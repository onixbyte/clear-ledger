package com.onixbyte.clearledger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"dev", "db", "jwt"})
class ClearLedgerServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
