package com.onixbyte.clearledger.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import com.onixbyte.clearledger.data.view.UserView;
import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UserAuthenticationToken;
import com.onixbyte.clearledger.service.UserService;
import com.onixbyte.guid.GuidCreator;
import com.onixbyte.simplejwt.TokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenResolver<DecodedJWT> tokenResolver;
    private final GuidCreator<Long> userIdCreator;
    private final UserService userService;
    private final RedisTemplate<String, User> userCache;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          TokenResolver<DecodedJWT> tokenResolver,
                          GuidCreator<Long> userIdCreator,
                          UserService userService,
                          RedisTemplate<String, User> userCache) {
        this.authenticationManager = authenticationManager;
        this.tokenResolver = tokenResolver;
        this.userIdCreator = userIdCreator;
        this.userService = userService;
        this.userCache = userCache;
    }

    /**
     * An API for user login.
     *
     * @param request user login request
     * @return user information
     */
    @PostMapping("/login")
    public ResponseEntity<UserView> login(@RequestBody UserLoginRequest request) {
        try {
            // perform authentication
            var _auth = authenticationManager.authenticate(UserAuthenticationToken.unauthenticated(
                    request.username(), request.password()));
            if (_auth instanceof UserAuthenticationToken authentication) {
                // create jwt
                var jwt = tokenResolver.createToken(Duration.ofDays(1), authentication.getName(), "ClearLedger :: User");
                // save data to cache server for 1 day
                userCache.opsForValue().set("clear-ledger:app:user:%s".formatted(authentication.getName()),
                        authentication.getDetails().toPersistent(), Duration.ofDays(1));
                // compose response entity
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Authorization", jwt)
                        .body(authentication.getDetails().toView());
            }

            throw new UnauthenticatedException("Server error!");
        } catch (AuthenticationException e) {
            throw new UnauthenticatedException();
        }
    }

    /**
     * API for user register.
     *
     * @param request user register request
     * @return created user information
     */
    @PostMapping("/register")
    public UserView register(@RequestBody UserRegisterRequest request) {
        // build user
        var user = User.builder()
                .id(userIdCreator.nextId())
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

        // ensure user can be created
        userService.saveUser(user);
        return user.toView();
    }

}
