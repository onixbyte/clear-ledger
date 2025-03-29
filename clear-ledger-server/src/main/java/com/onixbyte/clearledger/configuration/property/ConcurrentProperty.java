package com.onixbyte.clearledger.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Concurrent configuration properties.
 *
 * @author zihluwang
 */
@ConfigurationProperties(prefix = "app.concurrent")
public class ConcurrentProperty {

    /**
     * Maximum count of threads for non-IO operations. Default is {@code 20}.
     */
    private Integer maxTaskThreadCount = 20;

    /**
     * Default constructor.
     */
    public ConcurrentProperty() {
    }

    /**
     * Get the maximum count of threads for non-IO operations.
     *
     * @return maximum count of threads for non-IO operations
     */
    public Integer getMaxTaskThreadCount() {
        return maxTaskThreadCount;
    }

    /**
     * Set the maximum count of threads for non-IO operations.
     *
     * @param maxTaskThreadCount maximum count of threads for non-IO operations
     */
    public void setMaxTaskThreadCount(Integer maxTaskThreadCount) {
        this.maxTaskThreadCount = maxTaskThreadCount;
    }

}
