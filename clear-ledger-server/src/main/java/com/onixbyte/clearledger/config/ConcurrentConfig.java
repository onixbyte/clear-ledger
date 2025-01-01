package com.onixbyte.clearledger.config;

import com.onixbyte.clearledger.property.ConcurrentProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(ConcurrentProperty.class)
public class ConcurrentConfig {

    @Bean
    public ExecutorService ioThreadPool() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public ExecutorService taskThreadPool(ConcurrentProperty concurrentProperty) {
        return Executors.newFixedThreadPool(concurrentProperty.getMaxTaskThreadCount());
    }

}
