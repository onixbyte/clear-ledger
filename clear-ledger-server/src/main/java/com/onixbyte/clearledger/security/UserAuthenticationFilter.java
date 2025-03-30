package com.onixbyte.clearledger.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onixbyte.clearledger.data.response.BizExceptionResponse;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.clearledger.service.UserService;
import com.onixbyte.simplejwt.TokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A filter for authenticating users based on JWT tokens in HTTP requests.
 * <p>
 * This class extends {@link OncePerRequestFilter} to process incoming requests, validate JWT tokens,
 * and set the authenticated user in the security context using {@link UsernamePasswordToken}.
 *
 * @author zihluwang
 */
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final TokenResolver<DecodedJWT> tokenResolver;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    /**
     * Constructs a user authentication filter with required dependencies.
     *
     * @param tokenResolver the resolver for validating and decoding JWT tokens
     * @param objectMapper  the mapper for serialising response data
     * @param userService   the service for retrieving user information
     */
    public UserAuthenticationFilter(TokenResolver<DecodedJWT> tokenResolver,
                                    ObjectMapper objectMapper,
                                    UserService userService) {
        this.tokenResolver = tokenResolver;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    /**
     * Filters HTTP requests to authenticate users based on JWT tokens.
     * <p>
     * Extracts the JWT from the "Authorisation" header, validates it, retrieves the associated user,
     * and sets the authentication in the security context. If validation fails, an error response is
     * returned.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to proceed with the request
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var jwt = Optional.of(request)
                .map((req) -> req.getHeader("Authorisation"))
                .orElseGet(() -> request.getHeader("authorisation"));

        // no token was found, let the request pass through
        if (Objects.isNull(jwt) || jwt.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var resolvedToken = tokenResolver.resolve(jwt);
            var username = Optional.ofNullable(resolvedToken.getAudience())
                    .filter((audience) -> !audience.isEmpty())
                    .map(List::getFirst)
                    .orElse(null);

            if (Objects.isNull(username)) {
                writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("Unable to read user information, please log in again")
                        .build());
                return;
            }

            var user = userService.getUserByUsername(username);
            if (Objects.isNull(user)) {
                writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("Unable to read user information, please log in again")
                        .build());
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordToken.authenticated(user));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                    .message("Authentication failed, please log in again")
                    .build());
        } catch (BizException e) {
            writeResponse(response, e.composeResponse());
        }
    }

    /**
     * Writes a response entity to the HTTP response.
     *
     * @param response       the HTTP response to write to
     * @param responseEntity the response entity containing status and body
     * @param <T>            the type of the response body
     * @throws IOException if an I/O error occurs during writing
     */
    private <T> void writeResponse(HttpServletResponse response, ResponseEntity<T> responseEntity)
            throws IOException {
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        response.getWriter().flush();
    }

    /**
     * Writes a response with a specified status and data to the HTTP response.
     *
     * @param response the HTTP response to write to
     * @param status   the HTTP status code
     * @param data     the data to write as the response body
     * @param <T>      the type of the response data
     * @throws IOException if an I/O error occurs during writing
     */
    private <T> void writeResponse(HttpServletResponse response, HttpStatus status, T data)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(data));
        response.getWriter().flush();
    }
}