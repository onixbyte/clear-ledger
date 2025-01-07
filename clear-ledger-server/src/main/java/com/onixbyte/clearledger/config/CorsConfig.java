package com.onixbyte.clearledger.config;

import com.onixbyte.clearledger.property.CorsProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(CorsProperty.class)
public class CorsConfig {

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
