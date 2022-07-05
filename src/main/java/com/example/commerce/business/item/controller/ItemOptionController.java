package com.example.commerce.business.item.controller;

import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/option")
@RequiredArgsConstructor
public class ItemOptionController extends CommonUtil {

    private final ItemOptionService optionService;

    @PatchMapping("/stock/{optionId}")
    public SuccessResponse updateStock(@PathVariable Long optionId,
                                       @RequestParam("stock") int stock)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), optionService.updateStock(optionId, stock));
    }

    @PutMapping("/{optionId}")
    public SuccessResponse updateItemOption(@PathVariable Long optionId,
                                            @Valid @RequestBody ItemOptionAddRequestDto dto)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), optionService.updateItemOption(optionId, dto));
    }

    @DeleteMapping("/{optionId}")
    public SuccessResponse deleteItemOption(HttpServletRequest request,
                                            @PathVariable Long optionId)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), optionService.deleteItemOption(userId, optionId));
    }
}
