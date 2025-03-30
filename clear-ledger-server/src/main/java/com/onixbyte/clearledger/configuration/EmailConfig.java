package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.configuration.property.EmailProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for email settings.
 * <p>
 * This class enables the configuration of email settings in a Spring application by activating
 * the binding of {@link EmailProperty} properties. It uses the {@link EnableConfigurationProperties}
 * annotation to load email-related configuration from external sources, such as
 * {@code application.yml} or {@code application.properties}, without defining additional beans.
 *
 * @author zihluwang
 */
@Configuration
@EnableConfigurationProperties(EmailProperty.class)
public class EmailConfig {
}
