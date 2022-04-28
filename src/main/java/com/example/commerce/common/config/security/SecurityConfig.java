package com.example.commerce.common.config.security;

import com.example.commerce.business.auth.repository.TokenRepository;
import com.example.commerce.business.auth.util.TokenProvider;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.common.config.jwt.JWTCheckFilter;
import com.example.commerce.common.config.jwt.JWTLoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration // IoC 빈 등록
@EnableWebSecurity // 필터 체인 관리 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
        .and()
        .addFilter(corsFilter)
        .httpBasic().disable()
        .formLogin().disable()
        .addFilter(new JWTLoginFilter(authenticationManager(), objectMapper, tokenProvider, tokenRepository))
        .addFilter(new JWTCheckFilter(authenticationManager(), userRepository, tokenProvider))
        .authorizeRequests()
        .antMatchers("/api/v1/user/**")
        .hasRole("USER")
        .anyRequest()
        .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/docs/**") ;
    }
}
