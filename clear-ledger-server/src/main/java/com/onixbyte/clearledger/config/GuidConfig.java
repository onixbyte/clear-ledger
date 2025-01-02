package com.onixbyte.clearledger.config;

import com.onixbyte.guid.GuidCreator;
import com.onixbyte.guid.impl.SnowflakeGuidCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for generating GUIDs.
 *
 * @author zihluwang
 */
@Configuration
public class GuidConfig {

    /**
     * The GUID creator for user.
     *
     * @return a GUID creator used by user
     */
    @Bean
    public GuidCreator<Long> userIdCreator() {
        return new SnowflakeGuidCreator(0x0, 0x0);
    }

    /**
     * The GUID creator for ledger.
     *
     * @return a GUID creator used by ledger
     */
    @Bean
    public GuidCreator<Long> ledgerIdCreator() {
        return new SnowflakeGuidCreator(0x0, 0x1);
    }

    /**
     * The GUID creator for transaction.
     *
     * @return a GUID creator used by transaction
     */
    @Bean
    public GuidCreator<Long> transactionIdCreator() {
        return new SnowflakeGuidCreator(0x1, 0x0);
    }

}
