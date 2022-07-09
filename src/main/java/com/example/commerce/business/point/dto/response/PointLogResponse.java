package com.example.commerce.business.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PointLogResponse {

    private int point;

    private String pointUse;

    private String pointType;

    private LocalDateTime createDate;
}
