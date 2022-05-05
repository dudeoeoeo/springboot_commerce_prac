package com.example.commerce.common.bind;

import com.example.commerce.common.dto.CommonResponse;
import com.example.commerce.common.dto.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class BindingAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BindingAdvice.class);

    @Around("execution(* com.example.commerce.business.*.controller.*Controller.*(..))")
    public Object validationCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        String [] type = joinPoint.getSignature().getDeclaringTypeName().split("\\.");
        String method = joinPoint.getSignature().getName();

        Object [] parameters = Arrays.stream(joinPoint.getArgs()).map(arg -> !arg.toString().contains("error") ? arg : "").toArray();

        logger.info("[{}][{}] Args: [{}]", type[type.length - 1], method, Arrays.toString(parameters));

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult && ((BindingResult) arg).hasErrors()) {
                Map<String, Object> errorMap = new HashMap<>();
                final List<FieldError> fieldErrors = ((BindingResult) arg).getFieldErrors();
                for (FieldError fieldError : fieldErrors) {
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());

                    logger.warn("[{}][{}] Field [{}] Message [{}]", type[type.length - 1], method, fieldError.getField(), fieldError.getDefaultMessage());
                }
                return CommonResponse.of(ErrorCode.ILLEGAL_ARGUMENT, (BindingResult) fieldErrors);
            }
        }

        return joinPoint.proceed();
    }
}
