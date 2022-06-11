package com.example.commerce.business.cart.controller;

import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.service.CartService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController extends CommonUtil {

    private final CartService cartService;

    @GetMapping("/list")
    public SuccessResponse getCartItem(HttpServletRequest request,
                                       @RequestParam("searchPage") int searchPage,
                                       @RequestParam("searchCount") int searchCount)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), cartService.getCartItem(userId, searchPage, searchCount));
    }

    @PostMapping("/add")
    public SuccessResponse addCartItem(HttpServletRequest request,
                                       @Valid @RequestBody AddCartItem dto,
                                       BindingResult bindingResult)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), cartService.addCart(userId, dto));
    }

    @DeleteMapping("/{optionId}")
    public SuccessResponse deleteCartItem(HttpServletRequest request,
                                          @PathVariable Long optionId)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), cartService.deleteCartItem(userId, optionId));
    }
}
