package com.example.commerce.business.auth.util;

public interface JwtProperties {

    String HEADER = "Bearer ";
    String HEADER_STRING = "Authorization";
    String REFRESH_TOKEN_HEADER = "refreshToken";
    long lifeTime = 30;
    long refreshTime = 1440;
}
