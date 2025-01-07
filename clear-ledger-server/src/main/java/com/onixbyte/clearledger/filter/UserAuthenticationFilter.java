package com.onixbyte.clearledger.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UserAuthenticationToken;
import com.onixbyte.simplejwt.TokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
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

    public UserAuthenticationFilter(TokenResolver<DecodedJWT> tokenResolver,
                                    RedisTemplate<String, User> userCache) {
        this.tokenResolver = tokenResolver;
        this.userCache = userCache;
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
                    .orElseThrow(() -> new UnauthenticatedException("Please login to use this system."));

            var user = userCache.opsForValue().get("clear-ledger:app:user:%s".formatted(username));
            if (Objects.isNull(user)){
                throw new UnauthenticatedException("Cannot load user information, please login again.");
            }
            SecurityContextHolder.getContext().setAuthentication(UserAuthenticationToken.authenticated(user.toDomain()));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            throw new UnauthenticatedException("Please login to use this system.");
        }
    }

}
