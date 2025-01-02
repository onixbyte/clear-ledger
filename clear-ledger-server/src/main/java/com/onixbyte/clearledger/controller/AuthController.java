package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.request.UserLoginRequest;
import com.onixbyte.clearledger.data.request.UserRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * An API for user login.
     *
     * @param request user login request
     * @return user information
     */
    @PostMapping("/login")
    public ResponseEntity<UserDomain> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
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
