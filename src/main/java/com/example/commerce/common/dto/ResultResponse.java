package com.example.commerce.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponse {

    private String message;

    public static ResultResponse success(String message) {
        return ResultResponse.builder()
                .message(message)
                .build();
    }
}
