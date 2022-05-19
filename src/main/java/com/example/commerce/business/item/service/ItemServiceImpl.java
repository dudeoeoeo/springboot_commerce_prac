package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.repository.ItemRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final ItemImageService imageService;
    private final AmazonS3Service amazonS3Service;
    private final String FOLDER_NAME = "product";

    /**
     * TODO: static 결과 return class
     */
    @Transactional
    public void addItem(Long userId, ItemAddRequestDto dto, List<MultipartFile> multipartFiles) {
        final User user = userService.foundUserByUserId(userId);
        final List<String> imagePaths = amazonS3Service.uploadFiles(multipartFiles, FOLDER_NAME);

        final List<ItemImage> itemImages = imageService.createItemImage(user, imagePaths);
        final Item item = Item.newItem(user, dto, itemImages);

        itemRepository.save(item);
    }

    @Override
    public void updateItem(Long userId) {

    }

    @Override
    public void deleteItem(Long userId, Long itemId) {

    }
}