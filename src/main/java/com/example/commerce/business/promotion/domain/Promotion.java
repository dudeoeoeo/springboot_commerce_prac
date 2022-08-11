package com.example.commerce.business.promotion.domain;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.business.promotion.dto.request.UpdatePromotion;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promotion")
public class Promotion extends BaseTimeEntity {

    @Id @Column(name = "promotion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "sale_price")
    private int salePrice;

    @Column(name = "stock")
    private int stock;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // 쿠폰 사용 가능 여부
    @Column(name = "can_use_coupon")
    private boolean useCoupon;

    // 포인트 사용 가능 여부
    @Column(name = "can_use_point")
    private boolean usePoint;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static Promotion newPromotion(ItemOption itemOption, AddPromotion dto) {
        return Promotion.builder()
                .itemOption(itemOption)
                .discountPercent(dto.getDiscountPercent())
                .salePrice((int) (itemOption.getOptionPrice() * (dto.getDiscountPercent() / 100)))
                .stock(dto.getStock())
                .usePoint(dto.isUsePoint())
                .useCoupon(dto.isUseCoupon())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public void updateOrderStock(int orderStock) {
        if (this.stock < orderStock)
            throw new IllegalArgumentException("현재 재고수량이 부족합니다.");
        this.stock = this.stock - orderStock;
    }

    public void updatePromotion(UpdatePromotion dto) {
        this.discountPercent = dto.getDiscountPercent();
        this.salePrice = dto.getSalePrice();
        this.stock = dto.getStock();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.useCoupon = dto.isUseCoupon();
        this.usePoint = dto.isUsePoint();
    }

    public void updatePromotion(UpdatePromotion dto, ItemOption itemOption) {
        this.itemOption = itemOption;
        this.discountPercent = dto.getDiscountPercent();
        this.salePrice = dto.getSalePrice();
        this.stock = dto.getStock();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.useCoupon = dto.isUseCoupon();
        this.usePoint = dto.isUsePoint();
    }

    public void deletePromotion(User user) {
        this.deleteBy = user.getName();
        this.deleteDt = LocalDateTime.now();
        this.deleteYn = 1;
    }
}
