package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.configuration.property.CorsProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS).
 * <p>
 * This class provides configuration for CORS in a Spring application by defining a
 * {@link CorsConfigurationSource} bean. It utilises properties from {@link CorsProperty} to
 * customise CORS settings, such as allowed origins, methods, headers, and caching durations. The
 * {@link EnableConfigurationProperties} annotation enables binding of external configuration
 * properties to tailor CORS policies for the application.
 *
 * @author zihluwang
 */
@Configuration
@EnableConfigurationProperties(CorsProperty.class)
public class CorsConfig {

    /**
     * Configures a CORS configuration source based on provided properties.
     * <p>
     * This method creates a {@link CorsConfigurationSource} bean that applies CORS settings to all
     * endpoints (matched by the "/**" pattern). It constructs a {@link CorsConfiguration} instance
     * and populates it with values from the {@link CorsProperty} object, including:
     * <ul>
     *     <li>Allowed origins</li>
     *     <li>Allowed HTTP methods</li>
     *     <li>Allowed headers</li>
     *     <li>Credential support</li>
     *     <li>Maximum age for preflight caching</li>
     *     <li>Exposed headers</li>
     * </ul>
     * The configuration is then registered with a {@link UrlBasedCorsConfigurationSource} to
     * enable CORS support across the application. Each property is optional and applied only if
     * present in the provided {@link CorsProperty}.
     *
     * @param corsProperty the CORS properties used to configure the source
     * @return a {@link CorsConfigurationSource} configured with the specified CORS settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(CorsProperty corsProperty) {
        var configuration = new CorsConfiguration();

        // set allowed-origins
        Optional.of(corsProperty)
                .map(CorsProperty::getAllowedOrigins)
                .map((allowedOrigins) -> Arrays.stream(allowedOrigins).toList())
                .ifPresent(configuration::setAllowedOrigins);
        // set allowed-methods
        Optional.of(corsProperty)
                .map(CorsProperty::getAllowedMethods)
                .map((allowedMethods) -> Arrays.stream(allowedMethods)
                        .map(RequestMethod::name)
                        .toList())
                .ifPresent(configuration::setAllowedMethods);
        // set allowed-headers
        Optional.of(corsProperty)
                .map(CorsProperty::getAllowedHeaders)
                .map((allowedHeaders) -> Arrays.stream(allowedHeaders).toList())
                .ifPresent(configuration::setAllowedHeaders);
        // set allow-credentials
        Optional.of(corsProperty)
                .map(CorsProperty::getAllowCredentials)
                .ifPresent(configuration::setAllowCredentials);
        // set max-age
        Optional.of(corsProperty)
                .map(CorsProperty::getMaxAge)
                .ifPresent(configuration::setMaxAge);
        // set exposed-headers
        Optional.of(corsProperty)
                .map(CorsProperty::getExposedHeaders)
                .map((exposedHeaders) -> Arrays.stream(exposedHeaders).toList())
                .ifPresent(configuration::setExposedHeaders);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}