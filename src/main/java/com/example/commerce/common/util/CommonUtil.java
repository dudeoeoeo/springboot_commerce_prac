package com.example.commerce.common.util;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.auth.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

    private final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private static TokenProvider tokenProvider = new TokenProvider();

    protected Long getUserId(HttpServletRequest request) {
        return tokenProvider.getUserId(request.getHeader(JwtProperties.HEADER_STRING));
    }
}
