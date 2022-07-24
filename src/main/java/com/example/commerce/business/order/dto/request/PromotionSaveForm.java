package com.example.commerce.business.order.dto.request;

import com.example.commerce.business.promotion.domain.Promotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PromotionSaveForm {

    private Promotion promotion;

    private int stock;

    private int orderPrice;

    public static PromotionSaveForm newPromotionSaveForm(Promotion promotion, OrderPromotionForm form) {
        return PromotionSaveForm.builder()
                .promotion(promotion)
                .stock(form.getStock())
                .orderPrice(promotion.getSalePrice() * form.getStock())
                .build();
    }
}
