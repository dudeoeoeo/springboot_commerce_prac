package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.business.item.repository.ItemOptionRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemOptionServiceImpl implements ItemOptionService {

    private final ItemOptionRepository optionRepository;
    private final UserService userService;

    @Transactional
    public ItemOption addItemOption(ItemOptionAddRequestDto dto) {
        ItemOption itemOption = ItemOption.newItemOption(dto);
        return optionRepository.save(itemOption);
    }

    @Transactional
    public ResultResponse updateItemOption(Long itemOptionId, ItemOptionAddRequestDto dto) {
        final ItemOption option = findById(itemOptionId);

        option.updateItemOption(dto);
        optionRepository.save(option);

        return ResultResponse.success("상품 옵션이 변경 되었습니다.");
    }

    @Transactional
    public ResultResponse deleteItemOption(Long userId, Long itemOptionId) {
        final ItemOption option = findById(itemOptionId);
        option.deleteItemOption(userService.findUserByUserId(userId));

        optionRepository.save(option);

        return ResultResponse.success("상품 옵션이 삭제 되었습니다.");
    }

    public ItemOption findById(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션을 찾을 수 없습니다."));
    }

    @Transactional
    public ResultResponse updateStock(Long optionId, int stock) {
        final ItemOption option = findById(optionId);
        int saveStock = option.getOptionStock();
        if (saveStock >= stock + 1)
            return ResultResponse.success("수량이 변경되었습니다.");
        else
            return ResultResponse.success("최대수량은 " + saveStock + "개 입니다.");
    }

}
