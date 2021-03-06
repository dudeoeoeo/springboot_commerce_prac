package com.example.commerce.business.promotion.controller;

import com.example.commerce.business.promotion.dto.request.AddPromotion;
import com.example.commerce.business.promotion.service.PromotionService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion")
public class PromotionController extends CommonUtil {

    private final PromotionService promotionService;

    @PostMapping("/add")
    public SuccessResponse addPromotion(@Valid @RequestBody AddPromotion dto,
                                        BindingResult bindingResult)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), promotionService.addPromotion(dto));
    }
}
