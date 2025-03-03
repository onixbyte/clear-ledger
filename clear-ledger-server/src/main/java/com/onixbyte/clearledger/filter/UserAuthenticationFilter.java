package com.onixbyte.clearledger.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onixbyte.clearledger.data.biz.BizUser;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.response.BizExceptionResponse;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.simplejwt.TokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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
    private final RedisTemplate<String, BizUser> userCache;
    private final ObjectMapper objectMapper;

    public UserAuthenticationFilter(TokenResolver<DecodedJWT> tokenResolver,
                                    RedisTemplate<String, BizUser> userCache,
                                    ObjectMapper objectMapper) {
        this.tokenResolver = tokenResolver;
        this.userCache = userCache;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwt = Optional.of(request)
                .map((_request) -> _request.getHeader("Authorization"))
                .or(() -> Optional.of(request)
                        .map((_request) -> _request.getHeader("authorization")))
                .orElse(null);

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

            var user = userCache.opsForValue().get("clear-ledger:app:user:%s".formatted(username));
            if (Objects.isNull(user)) {
                writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("无法读取用户信息，请登录后再试")
                        .build());
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordToken.authenticated(user.toDomain()));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                    .message("身份信息读取失败，请重新登录后再试")
                    .build());
        }
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
