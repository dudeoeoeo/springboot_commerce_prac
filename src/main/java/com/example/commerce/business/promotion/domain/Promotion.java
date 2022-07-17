package com.example.commerce.business.promotion.domain;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
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

    @Id @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "stock")
    private int stock;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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
                .stock(dto.getStock())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
