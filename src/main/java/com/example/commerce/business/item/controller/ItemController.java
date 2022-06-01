package com.example.commerce.business.item.controller;

import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController extends CommonUtil {

    private final ItemService itemService;

    @GetMapping("/list")
    public SuccessResponse getItemList(@RequestParam("searchPage") int searchPage,
                                       @RequestParam("searchCount") int searchCount)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.getItemList(searchPage, searchCount));
    }

    @PostMapping("/add")
    public SuccessResponse addItem(HttpServletRequest request,
                                   @RequestPart("images") final List<MultipartFile> itemImages,
                                   @Valid @RequestPart("item_request") final ItemAddRequestDto dto,
                                   BindingResult bindingResult)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.addItem(userId, dto, itemImages));
    }
    @PutMapping("/{itemId}")
    public SuccessResponse updateItem(HttpServletRequest request,
                                      @PathVariable Long itemId,
                                      @Valid @RequestBody final ItemUpdateRequestDto dto,
                                      BindingResult bindingResult)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.updateItem(itemId, dto));
    }

    @PostMapping("/{imageId}/image")
    public SuccessResponse updateItemImage(HttpServletRequest request,
                                           @PathVariable Long imageId,
                                           @RequestPart("image") final MultipartFile itemImage)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.updateItemImage(imageId, itemImage));
    }

    @DeleteMapping("/{itemId}")
    public SuccessResponse deleteItem(HttpServletRequest request,
                                      @PathVariable Long itemId)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.deleteItem(userId, itemId));
    }

    @DeleteMapping("/{imageId}/image")
    public SuccessResponse deleteItemImage(HttpServletRequest request,
                                      @PathVariable Long imageId)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), itemService.deleteItemImage(userId, imageId));
    }
}
