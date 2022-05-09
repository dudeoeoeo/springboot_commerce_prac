package com.example.commerce.business.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    String token;
    String refreshToken;
}
