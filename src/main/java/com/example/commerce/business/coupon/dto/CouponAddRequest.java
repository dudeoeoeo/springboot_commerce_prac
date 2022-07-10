package com.example.commerce.business.coupon.dto;

import lombok.Getter;

@Getter
public class CouponAddRequest {

    private Long userId;
    private int condition;
    private int discount;
    private int expiredTime;
}
