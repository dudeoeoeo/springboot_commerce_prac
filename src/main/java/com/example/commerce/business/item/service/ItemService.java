package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
import com.example.commerce.business.item.dto.response.ItemDetailResponseDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import com.example.commerce.common.dto.ResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    /**
     * TODO: 각 method 들 dto 와 내부 공통 로직 및 aws s3 upload 추가
     */
    ResultResponse addItem(Long userId, ItemAddRequestDto dto, List<MultipartFile> multipartFiles);
    ResultResponse updateItem(Long itemId, ItemUpdateRequestDto dto);
    ResultResponse deleteItem(Long userId, Long itemId);
    Item findByItemId(Long itemId);
    Page<ItemResponseDto> getItemList(int searchPage, int searchCount);
//    ItemDetailResponseDto getItemDetail(Long itemId);
    ResultResponse updateItemImage(Long imageId, MultipartFile file);
    ResultResponse deleteItemImage(Long userId, Long imageId);
}
