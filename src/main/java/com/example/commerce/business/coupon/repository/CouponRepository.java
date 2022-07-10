package com.example.commerce.business.coupon.repository;

import com.example.commerce.business.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
