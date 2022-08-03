package com.example.commerce.business.promotion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PromotionLogResponse {

    private Long promotionLogId;
    private int stock;
    private int orderPrice;
    private int usePoint;
}
