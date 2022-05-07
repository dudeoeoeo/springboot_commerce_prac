package com.example.commerce.business.auth.service;

import com.example.commerce.business.auth.dto.response.TokenResponse;
import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.business.user.dto.request.UserSignUpRequest;

public interface AuthService {

    void signUp(UserSignUpRequest signUpRequest);
    TokenResponse signIn(UserLoginRequest loginRequest);
}
