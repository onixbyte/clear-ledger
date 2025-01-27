package com.onixbyte.clearledger.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.concurrent")
public class ConcurrentProperty {

    /**
     * Maximum count of threads for non-IO operations. Default is {@code 20}.
     */
    private Integer maxTaskThreadCount = 20;

}
