package com.example.commerce.business.item.service;

import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Override
    public void addItem(Long userId, ItemAddRequestDto dto, List<MultipartFile> multipartFiles) {

    }

    @Override
    public void updateItem(Long userId) {

    }

    @Override
    public void deleteItem(Long userId, Long itemId) {

    }
}
