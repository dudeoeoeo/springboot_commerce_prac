package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.common.dto.ResultResponse;

public interface ItemOptionService {

    ItemOption addItemOption(ItemOptionAddRequestDto dto);
    ResultResponse updateItemOption(Long itemOptionId, ItemOptionAddRequestDto dto);
    ResultResponse deleteItemOption(Long userId, Long itemOptionId);
    ItemOption findById(Long optionId);
}
