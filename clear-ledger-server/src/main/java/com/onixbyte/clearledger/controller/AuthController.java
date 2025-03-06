package com.onixbyte.clearledger.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import com.onixbyte.clearledger.data.response.UserResponse;
import com.onixbyte.clearledger.service.AuthService;
import com.onixbyte.guid.GuidCreator;
import com.onixbyte.simplejwt.TokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenResolver<DecodedJWT> tokenResolver;
    private final GuidCreator<String> userIdCreator;
    private final AuthService authService;

    @Autowired
    public AuthController(TokenResolver<DecodedJWT> tokenResolver,
                          GuidCreator<String> userIdCreator,
                          AuthService authService) {
        this.tokenResolver = tokenResolver;
        this.userIdCreator = userIdCreator;
        this.authService = authService;
    }

    /**
     * An API for user login.
     *
     * @param request user login request
     * @return user information
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest request) {
        var bizUser = authService.login(request.username(), request.password());
        var jwt = tokenResolver.createToken(Duration.ofDays(1), bizUser.username(), "ClearLedger :: User");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", jwt)
                .body(bizUser.toResponse());
    }

    /**
     * API for user register.
     *
     * @param request user register request
     * @return created user information
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        // build user
        var user = User.builder()
                .id(userIdCreator.nextId())
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

        var bizUser = authService.register(user);

        // create jwt
        var jwt = tokenResolver.createToken(Duration.ofDays(1), bizUser.username(), "ClearLedger :: User");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", jwt)
                .body(user.toResponse());
    }

}
