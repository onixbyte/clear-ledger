package com.onixbyte.clearledger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for internationalization (i18n) support.
 * <p>
 * Sets up the message source for retrieving localized messages from resource bundles.
 *
 * @author zihluwang
 */
@Configuration
public class I18nConfig {

    /**
     * Configures the message source for internationalization.
     *
     * @return a configured {@link ResourceBundleMessageSource}
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        source.setFallbackToSystemLocale(true);
        return source;
    }

}
