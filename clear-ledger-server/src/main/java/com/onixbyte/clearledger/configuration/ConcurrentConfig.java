package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.configuration.property.ConcurrentProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Configuration class for concurrent thread pools.
 * <p>
 * This class provides configuration for thread pools used in a Spring application to manage
 * concurrent task execution. It defines two {@link ExecutorService} beans: one for I/O-bound tasks
 * and another for general task execution, with properties customised via
 * {@link ConcurrentProperty}. The {@link EnableConfigurationProperties} annotation enables the
 * binding of external configuration properties to tailor the thread pool settings.
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
     * Configures a thread pool executor for I/O-bound operations.
     * <p>
     * This method creates an {@link ExecutorService} bean using a {@link ThreadPoolExecutor}
     * designed for I/O-intensive tasks. The thread pool is configured with:
     * <ul>
     *     <li>Core pool size: 5 threads</li>
     *     <li>Maximum pool size: 50 threads</li>
     *     <li>Keep-alive time: 60 seconds</li>
     *     <li>Queue capacity: 200 tasks (using a {@link LinkedBlockingQueue})</li>
     * </ul>
     * This setup is suitable for operations such as file handling or network requests, where
     * threads may frequently wait for I/O completion.
     *
     * @return an {@link ExecutorService} configured for I/O-bound tasks
     */
    @Bean
    public ExecutorService ioThreadPool() {
        return new ThreadPoolExecutor(
                5,
                50,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200)
        );
    }

    /**
     * Configures a thread pool executor for general task execution.
     * <p>
     * This method creates an {@link ExecutorService} bean using a {@link ThreadPoolExecutor}
     * tailored for general-purpose tasks. The thread pool's size is determined by the
     * {@link ConcurrentProperty#getMaxTaskThreadCount()} property, ensuring flexibility via
     * external configuration. It is configured with:
     * <ul>
     *     <li>Core pool size: equal to the maximum task thread count</li>
     *     <li>Maximum pool size: equal to the maximum task thread count</li>
     *     <li>Keep-alive time: 0 milliseconds (threads terminate immediately when idle)</li>
     *     <li>Queue capacity: 100 tasks (using a {@link LinkedBlockingQueue})</li>
     * </ul>
     * This configuration is ideal for tasks requiring a fixed number of threads with queuing
     * support.
     *
     * @param concurrentProperty the configuration properties defining the maximum task thread count
     * @return an {@link ExecutorService} configured for general task execution
     */
    @Bean
    public ExecutorService taskThreadPool(ConcurrentProperty concurrentProperty) {
        return new ThreadPoolExecutor(
                concurrentProperty.getMaxTaskThreadCount(),
                concurrentProperty.getMaxTaskThreadCount(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100)
        );
    }
}