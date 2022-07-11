package com.example.commerce.business.coupon.mapper;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.dto.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    default public CouponResponse toCouponResponse(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getId())
                .condition(coupon.getCondition())
                .discount(coupon.getDiscount())
                .expiredDate(coupon.getExpiredDate())
                .build();
    }
}
