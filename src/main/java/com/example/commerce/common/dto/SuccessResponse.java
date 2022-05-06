package com.example.commerce.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessResponse<T> {

    int statusCode = 200;
    String message;
    T data;

    public SuccessResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
    public SuccessResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> SuccessResponse of(int statusCode, String message, T data) {
        return new SuccessResponse<>(statusCode, message, data);
    };
    public static <T> SuccessResponse of(int statusCode, T data) {
        return new SuccessResponse<>(statusCode, data);
    };
}
