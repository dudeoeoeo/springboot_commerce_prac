package com.example.commerce.business.promotion.domain;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.order.dto.request.PromotionSaveForm;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Promotion 상품 log
 */
@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promotion_log")
public class PromotionLog extends BaseTimeEntity {

    @Id @Column(name = "promotion_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "stock")
    private int stock;

    @Column(name = "order_price")
    private int orderPrice;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "use_point")
    private int usePoint;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static PromotionLog newPromotionLog(PromotionSaveForm form, User user, Coupon coupon, int usePoint) {
        return PromotionLog.builder()
                .user(user)
                .promotion(form.getPromotion())
                .stock(form.getStock())
                .orderPrice(form.getOrderPrice())
                .coupon(coupon)
                .usePoint(usePoint)
                .build();
    }
}
