package com.example.commerce.business.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Component
public class TokenProvider {

    @Autowired
    private Environment env;

    private String SECRET;
    private String ISSUER;
    private Algorithm algorithm;

    @PostConstruct
    void init() {
        try {
            SECRET = env.getProperty("jwt.secret");
            ISSUER = env.getProperty("jwt.issuer");
            algorithm = Algorithm.HMAC512(SECRET);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public String generateToken(Long userId) {
        return JwtProperties.HEADER + JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("exp", Instant.now().getEpochSecond() + JwtProperties.lifeTime)
                .sign(algorithm);
    }

    public String refreshToken(Long userId) {
        return JwtProperties.HEADER + JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("exp", Instant.now().getEpochSecond() + JwtProperties.refreshTime)
                .sign(algorithm);
    }

    public VerifyResult verify(String token) {
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
        if (token == null || token.equals(""))
            throw new NullPointerException("Token을 찾을 수 없습니다.");
        token = replaceToken(token);
        return Long.valueOf(JWT.decode(token).getSubject());
    }

    public String replaceToken(String token) {
        if (token.startsWith(JwtProperties.HEADER))
            return token = token.replace(JwtProperties.HEADER, "");
        return token;
    }
}
