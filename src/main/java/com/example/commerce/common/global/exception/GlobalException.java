package com.example.commerce.common.global.exception;

import com.example.commerce.common.dto.CommonResponse;
import com.example.commerce.common.dto.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    private static Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = NullPointerException.class)
    public CommonResponse exception(NullPointerException e) {
        logger.warn("[NullPointerException] message: {}", e.getMessage());
        return CommonResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public CommonResponse exception(RuntimeException e) {
        logger.warn("[RuntimeException] message: {}", e.getMessage());
        return CommonResponse.of(ErrorCode.EXCEPTION, e.getMessage());
    }

}
