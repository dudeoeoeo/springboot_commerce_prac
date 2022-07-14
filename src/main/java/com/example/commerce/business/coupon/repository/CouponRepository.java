package com.example.commerce.business.coupon.repository;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT c FROM Coupon c WHERE c.user = :user AND c.couponUse = :couponUse AND " +
            "c.expiredDate >= :now ORDER BY c.id DESC")
    List<Coupon> findAllByUserAndCouponUseAndExpiredDate(@Param("user") User user, @Param("couponUse") boolean couponUse, @Param("now") LocalDateTime now);
}
