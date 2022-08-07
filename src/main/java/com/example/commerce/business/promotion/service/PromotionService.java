package com.example.commerce.business.promotion.service;

import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.business.promotion.dto.response.PromotionItemResponse;
import com.example.commerce.business.promotion.dto.response.PromotionResponse;
import com.example.commerce.common.dto.ResultResponse;
import org.springframework.data.domain.Page;

public interface PromotionService {

    ResultResponse addPromotion(AddPromotion dto);
    Page<PromotionItemResponse> getPromotionList(int page, int size);
    Promotion findById(Long promotionId);
    PromotionResponse getPromotionLogDetail(Long userId, Long promotionId);
    Page<PromotionResponse> getPromotionLogs(Long userId, int page, int size);
}
