package com.onixbyte.clearledger.filter;

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

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final TokenResolver<DecodedJWT> tokenResolver;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public UserAuthenticationFilter(TokenResolver<DecodedJWT> tokenResolver,
                                    ObjectMapper objectMapper,
                                    UserService userService) {
        this.tokenResolver = tokenResolver;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwt = Optional.of(request)
                .map((req) -> req.getHeader("Authorization"))
                .orElseGet(() -> request.getHeader("authorization"));

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
                        .message("无法读取用户信息，请登录后再试")
                        .build());
                return;
            }

            var user = userService.getUserByUsername(username);
            if (Objects.isNull(user)) {
                writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("无法读取用户信息，请登录后再试")
                        .build());
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordToken.authenticated(user));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                    .message("身份信息读取失败，请重新登录后再试")
                    .build());
        } catch (BizException e) {
            writeResponse(response, e.composeResponse());
        }
    }

    private <T> void writeResponse(HttpServletResponse response, ResponseEntity<T> responseEntity)
            throws IOException {
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        response.getWriter().flush();
    }

    private <T> void writeResponse(HttpServletResponse response, HttpStatus status, T data)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(data));
        response.getWriter().flush();
    }

}
