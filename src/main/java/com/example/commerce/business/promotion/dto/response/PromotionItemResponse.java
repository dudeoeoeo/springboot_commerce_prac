package com.example.commerce.business.promotion.dto.response;

import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PromotionItemResponse {

    private Long promotionId;
    private double discountPercent;
    private int salePrice;
    private int stock;
    private boolean useCoupon;
    private boolean usePoint;
    private LocalDate startDate;
    private LocalDate endDate;

    private ItemOptionResponseDto itemOption;

}
