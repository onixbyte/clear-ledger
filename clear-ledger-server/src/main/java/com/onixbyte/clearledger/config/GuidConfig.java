package com.onixbyte.clearledger.config;

import com.onixbyte.guid.GuidCreator;
import com.onixbyte.guid.impl.SnowflakeGuidCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuidConfig {

    @Bean
    public GuidCreator<Long> userIdCreator() {
        return new SnowflakeGuidCreator(0x0, 0x0);
    }

    @Bean
    public GuidCreator<Long> ledgerIdCreator() {
        return new SnowflakeGuidCreator(0x0, 0x1);
    }

    @Bean
    public GuidCreator<Long> transactionIdCreator() {
        return new SnowflakeGuidCreator(0x1, 0x0);
    }

}
