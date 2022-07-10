package com.example.commerce.business.coupon.domain;

import com.example.commerce.business.coupon.dto.CouponAddRequest;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon extends BaseTimeEntity {

    @Id @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "coupon_use")
    private boolean couponUse;

    @Column(name = "condition")
    private int condition;

    @Column(name = "discount")
    private int discount;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static Coupon newCoupon(User user, CouponAddRequest dto) {
        return Coupon.builder()
                .user(user)
                .couponUse(false)
                .condition(dto.getCondition())
                .discount(dto.getDiscount())
                .expiredDate(LocalDateTime.now().plusMonths(dto.getExpiredTime()))
                .build();
    }
}
