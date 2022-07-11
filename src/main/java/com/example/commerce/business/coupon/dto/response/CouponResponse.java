package com.example.commerce.business.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Builder @ToString
public class CouponResponse {

    private Long couponId;
    private int condition;
    private int discount;
    private LocalDateTime expiredDate;
}
