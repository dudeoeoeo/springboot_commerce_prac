package com.example.commerce.business.auth.util;

public interface JwtProperties {

    String HEADER = "Bearer ";
    String HEADER_STRING = "Authorization";
    long lifeTime = 30;
}
