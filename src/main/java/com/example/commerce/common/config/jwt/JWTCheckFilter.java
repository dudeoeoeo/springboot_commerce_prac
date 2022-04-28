package com.example.commerce.common.config.jwt;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.auth.util.TokenProvider;
import com.example.commerce.business.auth.util.VerifyResult;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public JWTCheckFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        if (token == null || !token.startsWith(JwtProperties.HEADER)) {
            chain.doFilter(request, response);
            return;
        }
        VerifyResult result = tokenProvider.verify(token);
        if (result.isResult()) {
            User user = userRepository.findById(result.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException("유저가 없습니다."));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user.getRole(), user, null)
            );
        }
        chain.doFilter(request, response);
    }
}
