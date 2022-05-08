package com.example.commerce.business.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(Long userId) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JwtProperties.HEADER + JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("exp", Instant.now().getEpochSecond() + JwtProperties.lifeTime)
                .sign(algorithm);
    }

    public String refreshToken(Long userId) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JwtProperties.HEADER + JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("exp", Instant.now().getEpochSecond() + JwtProperties.refreshTime)
                .sign(algorithm);
    }

    public VerifyResult verify(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        token = replaceToken(token);
        try {
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return VerifyResult.builder().userId(Long.valueOf(decode.getSubject())).result(true).build();
        } catch (Exception e) {
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().userId(Long.valueOf(decode.getSubject())).result(false).build();
        }
    }

    public Long getUserId(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        token = replaceToken(token);
        return Long.valueOf(JWT.decode(token).getSubject());
    }

    public String replaceToken(String token) {
        if (token.startsWith(JwtProperties.HEADER))
            return token = token.replace(JwtProperties.HEADER, "");
        return token;
    }
}
