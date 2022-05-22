package com.example.commerce.business.item.dao;

import com.example.commerce.business.item.dto.response.ItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemDAO {
    Page<ItemResponseDto> getItemList(Pageable pageable);
}
