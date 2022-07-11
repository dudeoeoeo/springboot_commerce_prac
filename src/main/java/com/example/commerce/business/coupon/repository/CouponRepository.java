package com.example.commerce.business.coupon.repository;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByUserAndCouponUse(User user, boolean couponUse);
}
