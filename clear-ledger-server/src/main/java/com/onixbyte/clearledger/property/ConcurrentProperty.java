package com.onixbyte.clearledger.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.concurrent")
public class ConcurrentProperty {

    private Integer maxTaskThreadCount = 20;

}
