package com.onixbyte.clearledger.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.response.BizExceptionResponse;
import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.clearledger.util.ResponseUtil;
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
    private final RedisTemplate<String, User> userCache;
    private final ResponseUtil responseUtil;

    public UserAuthenticationFilter(TokenResolver<DecodedJWT> tokenResolver,
                                    RedisTemplate<String, User> userCache, ResponseUtil responseUtil) {
        this.tokenResolver = tokenResolver;
        this.userCache = userCache;
        this.responseUtil = responseUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwt = Optional.ofNullable(request.getHeader("Authorization"))
                .or(() -> Optional.ofNullable(request.getHeader("authorization")))
                .orElse(null);

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
                responseUtil.writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("无法读取用户信息，请登录后再试")
                        .build());
                return;
            }

            var user = userCache.opsForValue().get("clear-ledger:app:user:%s".formatted(username));
            if (Objects.isNull(user)){
                responseUtil.writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                        .message("无法读取用户信息，请登录后再试")
                        .build());
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordToken.authenticated(user.toDomain()));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            responseUtil.writeResponse(response, HttpStatus.UNAUTHORIZED, BizExceptionResponse.builder()
                    .message("身份信息读取失败，请重新登录后再试")
                    .build());
        }
    }

}
