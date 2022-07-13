package com.example.commerce.business.coupon.controller;

import com.example.commerce.business.coupon.dto.request.CouponAddRequest;
import com.example.commerce.business.coupon.service.CouponService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController extends CommonUtil {

    private final CouponService couponService;

    @PostMapping("/add")
    public SuccessResponse addCoupon(@Valid @RequestBody CouponAddRequest dto,
                                     BindingResult bindingResult)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), couponService.addCoupon(dto));
    }

    @GetMapping("/list")
    public SuccessResponse getCouponList(HttpServletRequest request) {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), couponService.getCouponList(userId));
    }

    @GetMapping("/use/{couponId}")
    public SuccessResponse useCoupon(HttpServletRequest request,
                                     @PathVariable Long couponId,
                                     @RequestParam("price") int price)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), couponService.useCoupon(userId, couponId, price));
    }

    @GetMapping("/{couponId}")
    public SuccessResponse getCouponDetail(HttpServletRequest request,
                                           @PathVariable Long couponId) {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), couponService.getDetailCoupon(userId, couponId));
    }
}
