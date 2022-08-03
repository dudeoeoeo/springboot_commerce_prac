package com.example.commerce.business.promotion.dto.response;

import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PromotionResponse {

    private Long promotionId;
    private double discountPercent;
    private int salePrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private ItemOptionResponseDto itemOption;

    List<PromotionLogResponse> promotionLogResponses;
}
