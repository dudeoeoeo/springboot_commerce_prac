package com.example.commerce.business.promotion.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.business.promotion.dto.request.UpdatePromotion;
import com.example.commerce.business.promotion.dto.response.PromotionItemResponse;
import com.example.commerce.business.promotion.dto.response.PromotionResponse;
import com.example.commerce.business.promotion.mapper.PromotionMapper;
import com.example.commerce.business.promotion.repository.PromotionRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ItemOptionService itemOptionService;
    private final UserService userService;
    private final PromotionMapper promotionMapper;

    @Override
    @Transactional
    public ResultResponse addPromotion(AddPromotion dto) {
        final ItemOption itemOption = itemOptionService.findById(dto.getItemOptionId());
        final Promotion promotion = Promotion.newPromotion(itemOption, dto);
        promotionRepository.save(promotion);
        return ResultResponse.success("새로운 프로모션 상품을 등록했습니다.");
    }

    @Override
    public Page<PromotionItemResponse> getPromotionList(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("id").descending());
        LocalDate today = LocalDate.now();
        return promotionRepository.getPromotionList(request, today);
    }

    @Override
    public Promotion findById(Long promotionId) {
        final Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 프로모션 상품을 찾을 수 없습니다."));
        if (promotion.getDeleteYn() == 1)
            throw new IllegalThreadStateException("해당 프로모션이 종료되었습니다.");
        return promotion;
    }

    @Override
    public PromotionResponse getPromotionLogDetail(Long userId, Long promotionId) {
        final User user = userService.findUserByUserId(userId);
        final Promotion promotion = findById(promotionId);
        return promotionRepository.getPromotionLogDetail(user, promotion);
    }

    @Override
    public Page<PromotionResponse> getPromotionLogs(Long userId, int page, int size) {
        final User user = userService.findUserByUserId(userId);
        Pageable request = PageRequest.of(page, size);
        return promotionRepository.getPromotionLog(request, user);
    }

    @Override
    @Transactional
    public ResultResponse updatePromotion(Long promotionId, UpdatePromotion dto) {
        final Promotion promotion = findById(promotionId);
        if (dto.getItemOptionId() == 0) {
            promotion.updatePromotion(dto);
            return ResultResponse.success("promotion 상품을 업데이트 했습니다.");
        }

        final ItemOption itemOption = itemOptionService.findById(dto.getItemOptionId());
        promotion.updatePromotion(dto, itemOption);
        return ResultResponse.success("promotion 상품을 업데이트 했습니다.");
    }

    @Override
    @Transactional
    public ResultResponse deletePromotion(Long userId, Long promotionId) {
        final User user = userService.findUserByUserId(userId);
        final Promotion promotion = findById(promotionId);
        promotion.deletePromotion(user);
        return ResultResponse.success("프로모션 상품을 삭제했습니다.");
    }
}
