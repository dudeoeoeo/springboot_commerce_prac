package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import com.example.commerce.business.item.repository.ItemRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        final User user = userService.findUserByUserId(userId);
        final List<String> imagePaths = amazonS3Service.uploadFiles(multipartFiles, FOLDER_NAME);

        final List<ItemImage> itemImages = imageService.createItemImage(user, imagePaths);
        final Item item = Item.newItem(user, dto, itemImages);

        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequestDto dto) {
        final Item item = findByItemId(itemId);
        item.updateItemContent(dto);
        itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long userId, Long itemId) {
        final User user = userService.findUserByUserId(userId);
        final Item item = findByItemId(itemId);
        item.deleteItem(user);

        itemRepository.save(item);
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 상품을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<ItemResponseDto> getItemList(int searchPage, int searchCount) {
        Pageable request = PageRequest.of(getSearchPage(searchPage), searchCount);
        return itemRepository.getItemList(request);
    }

    public int getSearchPage(int searchPage) {
        return searchPage - 1 < 0 ? searchPage : searchPage - 1;
    }
}
