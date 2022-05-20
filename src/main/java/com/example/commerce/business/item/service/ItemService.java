package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    /**
     * TODO: 각 method 들 dto 와 내부 공통 로직 및 aws s3 upload 추가
     */
    void addItem(Long userId, ItemAddRequestDto dto, List<MultipartFile> multipartFiles);
    void updateItem(Long itemId, ItemUpdateRequestDto dto);
    void deleteItem(Long userId, Long itemId);
    Item findByItemId(Long itemId);
}
