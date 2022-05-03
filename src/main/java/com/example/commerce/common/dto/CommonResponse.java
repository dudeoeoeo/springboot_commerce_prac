package com.example.commerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String code;
    private String message;
    private int status;
    private List<CustomFieldError> errors;

    public static class CustomFieldError{
        private String field;
        private String value;
        private String reason;

        private CustomFieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private CustomFieldError(FieldError fieldError){
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue().toString();
            this.reason = fieldError.getDefaultMessage();
        }

        public String getField() { return field; }
        public String getValue() { return value; }
        public String getReason() { return reason; }
    }

    private void setErrorCode(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }

    private CommonResponse(ErrorCode errorCode, List<FieldError> errors) {
        setErrorCode(errorCode);
        this.errors = errors.stream().map(CustomFieldError::new).collect(Collectors.toList());
    }

    private CommonResponse(ErrorCode errorCode, String exceptionMessage) {
        setErrorCode(errorCode);
        this.errors = List.of(new CustomFieldError("", "", exceptionMessage));
    }

    public static CommonResponse of(ErrorCode errorCode){
        return new CommonResponse(errorCode, Collections.emptyList());
    }

    public static CommonResponse of(ErrorCode errorCode, BindingResult bindingResult){
        return new CommonResponse(errorCode, bindingResult.getFieldErrors());
    }

    public static CommonResponse of(ErrorCode errorCode, String exceptionMessage){
        return new CommonResponse(errorCode, exceptionMessage);
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public List<CustomFieldError> getErrors() { return errors; }

}
