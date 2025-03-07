package com.onixbyte.clearledger.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Configuration class for setting up concurrent thread pools. This class defines beans for different types of thread
 * pools used in the application. It uses properties defined in {@link ConcurrentProperty} to configure the
 * thread pools.
 *
 * @author zihluwang
 * @see ConcurrentProperty
 * @see ExecutorService
 * @see Executors
 */
@Configuration
@EnableConfigurationProperties(ConcurrentProperty.class)
public class ConcurrentConfig {

    /**
     * A cached thread pool for IO operations.
     *
     * @return a cached thread pool for IO operations
     */
    @Bean
    public ExecutorService ioThreadPool() {
        return Executors.newCachedThreadPool();
    }

    /**
     * A fixed thread pool for non-IO operations.
     *
     * @param concurrentProperty the concurrent property
     * @return a fixed thread pool for non-IO operations
     */
    @Bean
    public ExecutorService taskThreadPool(ConcurrentProperty concurrentProperty) {
        return Executors.newFixedThreadPool(concurrentProperty.getMaxTaskThreadCount());
    }

}
