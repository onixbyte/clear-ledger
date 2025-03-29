package com.onixbyte.clearledger.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Configuration properties for Cross-Origin Resource Sharing (CORS).
 * <p>
 * This class defines properties to configure CORS settings for a Spring Boot application. These
 * properties are bound to the {@code app.cors} prefix in the application's configuration files
 * (e.g., {@code application.yml} or {@code application.properties}). It provides a structured way
 * to manage CORS policies, such as allowed origins, methods, headers, and caching durations.
 *
 * @author zihluwang
 */
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperty {

    /**
     * Indicates whether credentials are allowed in CORS requests.
     * <p>
     * This property determines if the response to a request can be exposed when the credentials
     * flag is set to {@code true}. In the context of a preflight request, it specifies whether the
     * subsequent actual request can include credentials. For simple {@code GET} requests, which are
     * not preflighted, the absence of this header in the response (when credentials are used) will
     * cause the browser to ignore the response and prevent it from being returned to web content.
     */
    private Boolean allowCredentials;

    /**
     * Specifies the origins permitted to access the resource.
     * <p>
     * This property defines either a single origin (e.g., {@code "https://example.com"}) that
     * browsers should allow to access the resource, or the wildcard {@code "*"} to permit any
     * origin when credentials are not used. The default value is {@code {"*"}}.
     */
    private String[] allowedOrigins = {"*"};

    /**
     * Defines the HTTP headers allowed in CORS requests.
     * <p>
     * This property is used in response to a preflight request to indicate which HTTP headers
     * can be included in the actual request. It corresponds to the browser's
     * {@code Access-Control-Request-Headers} header, allowing the server to specify acceptable
     * headers for cross-origin requests.
     */
    private String[] allowedHeaders;

    /**
     * Specifies the HTTP methods permitted for accessing the resource.
     * <p>
     * This property lists the methods allowed in CORS requests, such as {@code GET},
     * {@code POST}, or {@code DELETE}. It is used in response to a preflight request to inform the
     * browser of supported methods. The default value includes {@code GET}, {@code POST},
     * {@code PUT}, {@code PATCH}, and {@code DELETE}.
     */
    private RequestMethod[] allowedMethods = {
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.PATCH,
            RequestMethod.DELETE
    };

    /**
     * Specifies the headers exposed to client-side JavaScript.
     * <p>
     * This property adds headers to the allowlist, making them accessible to JavaScript in
     * browsers (e.g., via {@code Response.headers}). It defines which server response headers can
     * be read by client-side code in a cross-origin context.
     */
    private String[] exposedHeaders;

    /**
     * Defines the duration (in seconds) that preflight request results can be cached.
     * <p>
     * This property indicates how long the browser can cache the response to a preflight
     * request, reducing the need for repeated preflight requests. The value is specified in
     * seconds.
     */
    private Long maxAge;

    /**
     * Default constructor for creating an instance of CORS properties.
     * <p>
     * This constructor initialises an empty instance of {@code CorsProperty}, allowing Spring
     * Boot to populate the properties from the configuration source.
     */
    public CorsProperty() {
    }

    /**
     * Retrieves the allowed credentials setting.
     *
     * @return {@code true} if credentials are allowed in CORS requests, {@code false} otherwise,
     * or {@code null} if not set
     */
    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    /**
     * Sets the allowed credentials setting.
     *
     * @param allowCredentials {@code true} to allow credentials in CORS requests,
     *                         {@code false} otherwise
     */
    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    /**
     * Retrieves the allowed origins.
     *
     * @return an array of origins permitted to access the resource
     */
    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    /**
     * Sets the allowed origins.
     *
     * @param allowedOrigins an array of origins to permit access to the resource
     */
    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Retrieves the allowed HTTP headers.
     *
     * @return an array of headers permitted in CORS requests
     */
    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    /**
     * Sets the allowed HTTP headers.
     *
     * @param allowedHeaders an array of headers to permit in CORS requests
     */
    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    /**
     * Retrieves the allowed HTTP methods.
     *
     * @return an array of HTTP methods permitted for the resource
     */
    public RequestMethod[] getAllowedMethods() {
        return allowedMethods;
    }

    /**
     * Sets the allowed HTTP methods.
     *
     * @param allowedMethods an array of HTTP methods to permit for the resource
     */
    public void setAllowedMethods(RequestMethod[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    /**
     * Retrieves the exposed headers.
     *
     * @return an array of headers exposed to client-side JavaScript
     */
    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    /**
     * Sets the exposed headers.
     *
     * @param exposedHeaders an array of headers to expose to client-side JavaScript
     */
    public void setExposedHeaders(String[] exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    /**
     * Retrieves the maximum age for caching preflight requests.
     *
     * @return the duration (in seconds) that preflight request results can be cached, or
     * {@code null} if not set
     */
    public Long getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the maximum age for caching preflight requests.
     *
     * @param maxAge the duration (in seconds) to cache preflight request results
     */
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }
}