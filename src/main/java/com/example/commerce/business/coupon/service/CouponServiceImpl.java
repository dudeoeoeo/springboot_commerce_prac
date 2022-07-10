package com.example.commerce.business.coupon.service;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.dto.CouponAddRequest;
import com.example.commerce.business.coupon.repository.CouponRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserService userService;

    @Override
    @Transactional
    public ResultResponse addCoupon(CouponAddRequest dto) {
        final User user = userService.findUserByUserId(dto.getUserId());
        final Coupon coupon = Coupon.newCoupon(user, dto);
        couponRepository.save(coupon);
        return ResultResponse.success("새로운 쿠폰을 발급했습니다.");
    }
}
