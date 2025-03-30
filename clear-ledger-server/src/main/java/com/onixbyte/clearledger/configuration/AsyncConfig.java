package com.onixbyte.clearledger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration class for asynchronous task execution.
 * <p>
 * This class enables asynchronous processing in a Spring application by configuring a thread pool
 * task executor. It uses the {@link EnableAsync} annotation to activate Spring's asynchronous
 * method execution capability and defines a custom {@link Executor} bean to manage a pool of
 * threads for handling asynchronous tasks.
 *
 * @author zihluwang
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Creates and configures a thread pool task executor for asynchronous operations.
     * <p>
     * This method defines a {@link ThreadPoolTaskExecutor} bean with a customised thread pool
     * configuration. The executor is designed to handle asynchronous tasks efficiently by
     * maintaining a core pool of threads, allowing for a maximum number of threads, and queuing
     * excess tasks. The configuration includes:
     * <ul>
     *     <li>Core pool size: 5 threads</li>
     *     <li>Maximum pool size: 10 threads</li>
     *     <li>Queue capacity: 100 tasks</li>
     *     <li>Thread name prefix: "AsyncThread-"</li>
     *     <li>Keep-alive time: 60 seconds</li>
     *     <li>Wait for tasks to complete on shutdown: true</li>
     *     <li>Await termination timeout: 30 seconds</li>
     * </ul>
     * The executor is initialised and returned as a bean for use in asynchronous method execution.
     *
     * @return an {@link Executor} instance configured for asynchronous task execution
     */
    @Bean
    public Executor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();
        return executor;
    }
}