package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.configuration.property.EmailProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EmailProperty.class)
public class EmailConfig {
}
