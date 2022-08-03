package com.example.commerce.business.promotion.mapper;

import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.domain.PromotionLog;
import com.example.commerce.business.promotion.dto.response.PromotionLogResponse;
import com.example.commerce.business.promotion.dto.response.PromotionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    default public PromotionResponse toPromotionLogResponse(Promotion promotion, List<PromotionLog> log) {
        List<PromotionLogResponse> responses = new ArrayList<>();
        for (PromotionLog p : log) {
            responses.add(
                    PromotionLogResponse.builder()
                    .promotionLogId(p.getId())
                    .stock(p.getStock())
                    .orderPrice(p.getOrderPrice())
                    .usePoint(p.getUsePoint())
                    .build());
        }

        final ItemOptionResponseDto itemOptionResponseDto = ItemOptionResponseDto.builder()
                .id(promotion.getItemOption().getId())
                .optionPrice(promotion.getItemOption().getOptionPrice())
                .optionStock(promotion.getItemOption().getOptionStock())
                .optionSize(promotion.getItemOption().getOptionSize())
                .optionColor(promotion.getItemOption().getOptionColor())
                .optionWeight(promotion.getItemOption().getOptionWeight())
                .build();

        return PromotionResponse.builder()
                .promotionId(promotion.getId())
                .discountPercent(promotion.getDiscountPercent())
                .salePrice(promotion.getSalePrice())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .itemOption(itemOptionResponseDto)
                .promotionLogResponses(responses)
                .build();
    }
}
