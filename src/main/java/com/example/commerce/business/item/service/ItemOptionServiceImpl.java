package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.business.item.repository.ItemOptionRepository;
import com.example.commerce.business.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemOptionServiceImpl implements ItemOptionService {

    private final ItemOptionRepository optionRepository;

    @Transactional
    public void addItemOption(ItemOptionAddRequestDto dto) {
        ItemOption itemOption = ItemOption.newItemOption(dto);
        optionRepository.save(itemOption);
    }

    @Transactional
    public void updateItemOption(Long itemOptionId, ItemOptionAddRequestDto dto) {
        final ItemOption option = findById(itemOptionId);

        option.updateItemOption(dto);
        optionRepository.save(option);
    }

    @Transactional
    public void deleteItemOption(User user, Long itemOptionId) {
        final ItemOption option = findById(itemOptionId);
        option.deleteItemOption(user);

        optionRepository.save(option);
    }

    public ItemOption findById(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션을 찾을 수 없습니다."));
    }

}
