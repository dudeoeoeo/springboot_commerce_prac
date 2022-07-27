package com.example.commerce.business.promotion.service;

import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.common.dto.ResultResponse;

public interface PromotionService {

    ResultResponse addPromotion(AddPromotion dto);
    Promotion findById(Long promotionId);
}
