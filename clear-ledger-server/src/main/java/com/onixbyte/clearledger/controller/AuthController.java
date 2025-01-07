package com.onixbyte.clearledger.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UserAuthenticationToken;
import com.onixbyte.simplejwt.TokenResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenResolver<DecodedJWT> tokenResolver;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          TokenResolver<DecodedJWT> tokenResolver) {
        this.authenticationManager = authenticationManager;
        this.tokenResolver = tokenResolver;
    }

    /**
     * An API for user login.
     *
     * @param request user login request
     * @return user information
     */
    @PostMapping("/login")
    public ResponseEntity<UserDomain> login(@RequestBody UserLoginRequest request) {
        log.info("username = {}, password = {}", request.username(), request.password());
        try {
            var _auth = authenticationManager.authenticate(UserAuthenticationToken.unauthenticated(
                    request.username(), request.password()));
            if (_auth instanceof UserAuthenticationToken authentication) {
                var jwt = tokenResolver.createToken(Duration.ofDays(1), authentication.getName(), "ClearLedger :: User");
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Authorization", jwt)
                        .body(authentication.getDetails());
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
    public UserDomain register(@RequestBody UserRegisterRequest request) {
        return null;
    }

}
