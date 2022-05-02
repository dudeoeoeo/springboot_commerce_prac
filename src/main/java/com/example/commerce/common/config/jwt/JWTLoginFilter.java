package com.example.commerce.common.config.jwt;

import com.example.commerce.business.auth.domain.Token;
import com.example.commerce.business.auth.repository.TokenRepository;
import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.auth.util.TokenProvider;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.common.config.security.auth.UserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    public JWTLoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, TokenProvider tokenProvider, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        setFilterProcessesUrl("/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserLoginRequest userLogin = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
        System.out.println("JWTLoginFilter userLogin " + userLogin.toString());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userLogin.getEmail(), userLogin.getPassword(), null
        );

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        UserDetail userDetail = (UserDetail) authResult.getPrincipal();
        response.addHeader(JwtProperties.HEADER_STRING, tokenProvider.generateToken(userDetail.getUser().getId()));
        String refreshToken = generateRefreshToken(userDetail.getUser());
        response.addHeader(JwtProperties.REFRESH_TOKEN_HEADER, refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        System.out.println("JWTLoginFilter unsuccessfulAuthentication " + failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }

    public String generateRefreshToken(User user) {
        String refreshToken = tokenProvider.refreshToken(user.getId());
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();
        return tokenRepository.save(token).getRefreshToken();
    }
}
