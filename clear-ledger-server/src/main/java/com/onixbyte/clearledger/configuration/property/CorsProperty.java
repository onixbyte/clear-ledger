package com.onixbyte.clearledger.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMethod;

@ConfigurationProperties(prefix = "app.cors")
public class CorsProperty {

    /**
     * This header indicates whether the response to the request can be exposed when the credentials
     * flag is true. When used as part of a response to a preflight request, this indicates whether
     * the actual request can be made using credentials. Note that simple {@code GET} requests are
     * not preflighted, and so if a request is made for a resource with credentials, if this header
     * is not returned with the resource, the response is ignored by the browser and not returned to web content.
     */
    private Boolean allowCredentials;

    /**
     * This specifies either a single origin which tells browsers to allow that origin to access the
     * resource; or else — for requests without credentials — the {@code *} wildcard tells browsers
     * to allow any origin to access the resource.
     */
    private String[] allowedOrigins = {"*"};

    /**
     * This header is used in response to a preflight request to indicate which HTTP headers can be
     * used when making the actual request. This header is the server side response to the browser's
     * {@code Access-Control-Request-Headers} header.
     */
    private String[] allowedHeaders;

    /**
     * This header specifies the method or methods allowed when accessing the resource. This is used
     * in response to a preflight request.
     */
    private RequestMethod[] allowedMethods = {
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.PATCH,
            RequestMethod.DELETE
    };

    /**
     * This header adds the specified headers to the allowlist that JavaScript (such as
     * {@code Response.headers}) in browsers is allowed to access.
     */
    private String[] exposedHeaders;

    /**
     * This header indicates how long the results of a preflight request can be cached. For an
     * example of a preflight request.
     */
    private Long maxAge;

    public CorsProperty() {
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public RequestMethod[] getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(RequestMethod[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    public void setExposedHeaders(String[] exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }
}
