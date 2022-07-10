package com.example.commerce.business.coupon.service;

import com.example.commerce.business.coupon.dto.CouponAddRequest;
import com.example.commerce.common.dto.ResultResponse;

public interface CouponService {

    ResultResponse addCoupon(CouponAddRequest dto);
}
