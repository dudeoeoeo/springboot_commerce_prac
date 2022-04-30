package com.example.commerce.business.auth.controller;

import com.example.commerce.business.user.dto.request.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signUp")
    public void signUp(@RequestBody UserLoginRequest userLoginRequest) {
        logger.info("[AuthController][signUp] called {}", userLoginRequest.toString());
    }

//    @PostMapping("/login")
//    public void login(@RequestBody UserLoginRequest userLoginRequest) {
//        logger.info("[AuthController][login] called {}", userLoginRequest.toString());
//    }
}
