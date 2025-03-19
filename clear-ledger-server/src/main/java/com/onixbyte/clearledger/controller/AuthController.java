package com.onixbyte.clearledger.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import com.onixbyte.clearledger.data.response.UserResponse;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.service.AuthService;
import com.onixbyte.clearledger.service.VerificationCodeService;
import com.onixbyte.guid.GuidCreator;
import com.onixbyte.simplejwt.TokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenResolver<DecodedJWT> tokenResolver;
    private final GuidCreator<String> userIdCreator;
    private final AuthService authService;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public AuthController(TokenResolver<DecodedJWT> tokenResolver,
                          GuidCreator<String> userIdCreator,
                          AuthService authService, VerificationCodeService verificationCodeService) {
        this.tokenResolver = tokenResolver;
        this.userIdCreator = userIdCreator;
        this.authService = authService;
        this.verificationCodeService = verificationCodeService;
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

        // create jwt
        var jwt = tokenResolver.createToken(Duration.ofDays(1), bizUser.username(), "ClearLedger :: User");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", jwt)
                .body(user.toResponse());
    }

    @GetMapping("/verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String audience) {
        authService.sendVerificationCode(audience);
        return ResponseEntity.noContent().build();
    }

}
