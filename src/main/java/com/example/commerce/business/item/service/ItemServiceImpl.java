package com.example.commerce.business.item.service;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.service.CategoryService;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import com.example.commerce.business.item.repository.ItemRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final ItemImageService imageService;
    private final ItemOptionService optionService;
    private final AmazonS3Service amazonS3Service;
    private final CategoryService categoryService;
    private final String FOLDER_NAME = "product";

    /**
      * TODO: item option 추가
     */
    @Transactional
    public ResultResponse addItem(Long userId, ItemAddRequestDto dto, List<MultipartFile> multipartFiles) {
        final User user = userService.findUserByUserId(userId);

        final List<ItemOption> optionList = dto.getItemOptions()
                .stream()
                .map(option -> optionService.addItemOption(option))
                .collect(Collectors.toList());
        final List<String> imagePaths = amazonS3Service.uploadFiles(multipartFiles, FOLDER_NAME);
        final List<ItemImage> itemImages = imageService.createItemImage(user, imagePaths);
        final Category category = categoryService.findById(dto.getCategoryId());
        final Item item = Item.newItem(user, dto, itemImages, category, optionList);

        itemRepository.save(item);
        return ResultResponse.success("새로운 상품이 등록되었습니다.");
    }

    @Transactional
    public ResultResponse updateItem(Long itemId, ItemUpdateRequestDto dto) {
        final Item item = findByItemId(itemId);
        item.updateItemContent(dto);
        itemRepository.save(item);
        return ResultResponse.success("상품 정보가 업데이트 되었습니다.");
    }

    @Transactional
    public ResultResponse deleteItem(Long userId, Long itemId) {
        final User user = userService.findUserByUserId(userId);
        final Item item = findByItemId(itemId);
        item.deleteItem(user);

        itemRepository.save(item);
        return ResultResponse.success("상품을 삭제 했습니다.");
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

//    @Transactional(readOnly = true)
//    public Optional<Item> findByCategoryId(Long categoryId) {
//        return itemRepository.findByCategoryId(categoryId);
//    }

    @Transactional
    public ResultResponse updateItemImage(Long imageId, MultipartFile file) {
        final String imagePath = amazonS3Service.uploadFile(file, FOLDER_NAME);
        imageService.updateItemImage(imageId, imagePath);

        return ResultResponse.success("상품 이미지를 변경했습니다.");
    }

    @Transactional
    public ResultResponse deleteItemImage(Long userId, Long imageId) {
        final User user = userService.findUserByUserId(userId);
        imageService.deleteItemImage(imageId, user);

        return ResultResponse.success("상품 이미지를 삭제했습니다.");
    }

    public int getSearchPage(int searchPage) {
        return searchPage - 1 < 0 ? searchPage : searchPage - 1;
    }
}
