package com.example.commerce.business.coupon.service;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.dto.request.CouponAddRequest;
import com.example.commerce.business.coupon.dto.response.CouponResponse;
import com.example.commerce.business.coupon.mapper.CouponMapper;
import com.example.commerce.business.coupon.repository.CouponRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserService userService;
    private final CouponMapper couponMapper;

    @Override
    @Transactional
    public ResultResponse addCoupon(CouponAddRequest dto) {
        final User user = userService.findUserByUserId(dto.getUserId());
        final Coupon coupon = Coupon.newCoupon(user, dto);
        couponRepository.save(coupon);
        return ResultResponse.success("새로운 쿠폰을 발급했습니다.");
    }

    @Override
    public List<CouponResponse> getCouponList(Long userId) {
        final User user = userService.findUserByUserId(userId);
        final List<Coupon> coupons = couponRepository.findAllByUserAndCouponUse(user, false);
        return coupons.stream()
                .map(c -> couponMapper.toCouponResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse getDetailCoupon(Long userId, Long couponId) {
        final User user = userService.findUserByUserId(userId);
        final Coupon coupon = findById(couponId);
        if (user.getId() != coupon.getUser().getId())
            throw new IllegalArgumentException("해당 유저의 쿠폰이 아닙니다.");
        if (coupon.isCouponUse())
            throw new IllegalArgumentException("이미 사용한 쿠폰입니다.");
        return couponMapper.toCouponResponse(coupon);
    }

    @Override
    public ResultResponse useCoupon(Long userId, Long couponId, int price) {
        final Coupon coupon = findById(couponId);
        if (userId != coupon.getUser().getId())
            throw new IllegalArgumentException("해당 회원의 쿠폰이 아닙니다.");
        if (price < coupon.getCondition())
            throw new IllegalArgumentException("쿠폰 사용 조건이 맞지 않습니다.");
        return ResultResponse.success("쿠폰을 적용했습니다.");
    }

    @Override
    public Coupon findById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
    }
}
