package com.example.commerce.business.auth.service;

import com.example.commerce.business.auth.dto.response.TokenResponse;
import com.example.commerce.business.user.dto.request.UserLoginRequest;

public interface AuthService {

    TokenResponse signIn(UserLoginRequest loginRequest);
}
