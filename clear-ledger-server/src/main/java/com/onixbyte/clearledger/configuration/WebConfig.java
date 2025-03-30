package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.security.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings.
 * <p>
 * This class configures web-related settings in a Spring application by implementing
 * {@link WebMvcConfigurer}. It defines a custom {@link UserInterceptor} bean and registers it to
 * intercept all HTTP requests, enabling additional processing or validation logic across the
 * application.
 *
 * @author zihluwang
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Provides a user interceptor for request processing.
     * <p>
     * This method creates a {@link UserInterceptor} bean to be used in the application's request
     * handling pipeline. The interceptor can perform tasks such as authentication checks or
     * request logging.
     *
     * @return a configured {@link UserInterceptor} instance
     */
    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    /**
     * Registers interceptors for HTTP request handling.
     * <p>
     * This method overrides the {@link WebMvcConfigurer#addInterceptors} method to register the
     * {@link UserInterceptor} with the application's interceptor registry. It applies the
     * interceptor to all URL patterns ("/**"), ensuring it processes every incoming request.
     *
     * @param registry the {@link InterceptorRegistry} to add interceptors to
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor())
                .addPathPatterns("/**");
    }
}