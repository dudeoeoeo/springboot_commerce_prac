package com.example.commerce.business.coupon.service;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.dto.request.CouponAddRequest;
import com.example.commerce.business.coupon.dto.response.CouponResponse;
import com.example.commerce.common.dto.ResultResponse;

import java.util.List;

public interface CouponService {

    ResultResponse addCoupon(CouponAddRequest dto);
    List<CouponResponse> getCouponList(Long userId);
    CouponResponse getDetailCoupon(Long userId, Long couponId);
    ResultResponse useCoupon(Long userId, Long couponId, int price);
    Coupon findById(Long couponId);
}
