package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.business.user.domain.User;

public interface ItemOptionService {

    void addItemOption(ItemOptionAddRequestDto dto);
    void updateItemOption(Long itemOptionId, ItemOptionAddRequestDto dto);
    void deleteItemOption(User user, Long itemOptionId);
    ItemOption findById(Long optionId);
}
