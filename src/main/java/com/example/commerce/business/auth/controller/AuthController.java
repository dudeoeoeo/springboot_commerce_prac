package com.example.commerce.business.auth.controller;

import com.example.commerce.business.auth.service.AuthService;
import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.business.user.dto.request.UserSignUpRequest;
import com.example.commerce.common.dto.CommonResponse;
import com.example.commerce.common.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/signUp")
    public void signUp(@Valid @RequestBody UserSignUpRequest signUpRequest) {
        logger.info("[AuthController][signUp] called {}", signUpRequest.toString());
    }

    @PostMapping("/signIn")
    public SuccessResponse login(@Valid @RequestBody UserLoginRequest loginRequest, BindingResult bindingResult) {
        logger.info("[AuthController][signIn] called {}", loginRequest.toString());
        return SuccessResponse.of(HttpStatus.OK.value(), authService.signIn(loginRequest));
    }
}
