package com.example.commerce.business.promotion.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.business.promotion.repository.PromotionRepository;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ItemOptionService itemOptionService;

    @Override
    @Transactional
    public ResultResponse addPromotion(AddPromotion dto) {
        final ItemOption itemOption = itemOptionService.findById(dto.getItemOptionId());
        final Promotion promotion = Promotion.newPromotion(itemOption, dto);
        promotionRepository.save(promotion);
        return ResultResponse.success("새로운 프로모션 상품을 등록했습니다.");
    }
}
