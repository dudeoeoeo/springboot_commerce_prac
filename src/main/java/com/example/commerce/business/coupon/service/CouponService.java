package com.example.commerce.business.coupon.service;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.dto.CouponAddRequest;
import com.example.commerce.business.coupon.dto.response.CouponResponse;
import com.example.commerce.common.dto.ResultResponse;

import java.util.List;

public interface CouponService {

    ResultResponse addCoupon(CouponAddRequest dto);
    List<CouponResponse> getCouponList(Long userId);
    CouponResponse getDetailCoupon(Long userId, Long couponId);
    Coupon findById(Long couponId);
}
