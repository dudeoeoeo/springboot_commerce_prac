package com.example.commerce.common.config.jwt.handler;

import com.example.commerce.business.auth.util.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtExceptionFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            logger.info("[JwtExceptionFilter]");
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            logger.info("[JwtExceptionFilter] error message: {}", e.getMessage());
            setErrorResponse(HttpStatus.OK, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
        logger.info("[setErrorResponse] error: {}, {}, {}", status, response, e.getMessage());

        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("status", status);
        errorMap.put("message", e.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorMap);
    }
}
