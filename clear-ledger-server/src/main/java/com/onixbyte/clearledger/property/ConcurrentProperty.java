package com.onixbyte.clearledger.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.concurrent")
public class ConcurrentProperty {

    /**
     * Maximum count of threads for non-IO operations. Default is {@code 20}.
     */
    private Integer maxTaskThreadCount = 20;

    public ConcurrentProperty() {
    }

    public Integer getMaxTaskThreadCount() {
        return maxTaskThreadCount;
    }

    public void setMaxTaskThreadCount(Integer maxTaskThreadCount) {
        this.maxTaskThreadCount = maxTaskThreadCount;
    }

}
