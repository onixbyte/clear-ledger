package com.onixbyte.clearledger.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import com.onixbyte.clearledger.data.response.UserResponse;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.service.AuthService;
import com.onixbyte.guid.GuidCreator;
import com.onixbyte.simplejwt.TokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Controller for handling authentication-related operations.
 * <p>
 * This class provides REST endpoints for user authentication, including login, registration, and
 * verification code management. It integrates with {@link AuthService} for business logic and uses
 * JWT tokens for secure user sessions.
 *
 * @author zihluwang
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenResolver<DecodedJWT> tokenResolver;
    private final GuidCreator<String> userIdCreator;
    private final AuthService authService;

    /**
     * Constructs an authentication controller with required dependencies.
     *
     * @param tokenResolver the resolver for creating and managing JWT tokens
     * @param userIdCreator the creator for generating unique user identifiers
     * @param authService the service handling authentication logic
     */
    @Autowired
    public AuthController(TokenResolver<DecodedJWT> tokenResolver,
                          GuidCreator<String> userIdCreator,
                          AuthService authService) {
        this.tokenResolver = tokenResolver;
        this.userIdCreator = userIdCreator;
        this.authService = authService;
    }

    /**
     * Handles user login requests.
     * <p>
     * This endpoint authenticates a user based on the provided username and password, returning
     * user information and a JWT token in the response header if successful.
     *
     * @param request the login request containing username and password
     * @return a {@link ResponseEntity} with user information and an "Authorization" header containing the JWT
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
     * Handles user registration requests.
     * <p>
     * This endpoint registers a new user with the provided details, including username, password,
     * email, and verification code. It validates the input, generates a unique user ID, and
     * returns the created user's information along with a JWT token.
     *
     * @param request the registration request containing user details
     * @return a {@link ResponseEntity} with the created user's information and an "Authorization" header containing the JWT
     * @throws BizException if any required field is missing or the verification code is invalid
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        if (Objects.isNull(request.username()) || request.username().isBlank()) {
            throw new BizException(HttpStatus.BAD_REQUEST, "用户名不能为空");
        }

        if (Objects.isNull(request.password()) || request.password().isBlank()) {
            throw new BizException(HttpStatus.BAD_REQUEST, "用户密码不能为空");
        }

        if (Objects.isNull(request.email()) || request.email().isBlank()) {
            throw new BizException(HttpStatus.BAD_REQUEST, "用户邮箱不能为空");
        }

        var _verificationCode = authService.getVerificationCode(request.email());
        if (Objects.isNull(request.verificationCode()) || Objects.isNull(_verificationCode) || !_verificationCode.equals(request.verificationCode())) {
            throw new BizException(HttpStatus.BAD_REQUEST, "邮箱验证码错误");
        }

        // build user
        var user = User.builder()
                .id(userIdCreator.nextId())
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

        var bizUser = authService.register(user);

        var jwt = tokenResolver.createToken(Duration.ofDays(1), bizUser.username(), "ClearLedger :: User");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", jwt)
                .body(user.toResponse());
    }

    /**
     * Sends a verification code to the specified audience.
     * <p>
     * This endpoint triggers the sending of a verification code to the provided email address,
     * typically used during registration or authentication processes.
     *
     * @param audience the email address to receive the verification code
     * @return a {@link ResponseEntity} with no content (HTTP 204) upon successful sending
     */
    @GetMapping("/verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String audience) {
        authService.sendVerificationCode(audience);
        return ResponseEntity.noContent().build();
    }
}