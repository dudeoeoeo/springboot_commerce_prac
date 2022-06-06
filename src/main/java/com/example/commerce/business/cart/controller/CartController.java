package com.example.commerce.business.cart.controller;

import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.service.CartService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController extends CommonUtil {

    private final CartService cartService;

    @PostMapping("/add")
    public SuccessResponse addCart(HttpServletRequest request,
                                       @Valid @RequestBody AddCartItem dto,
                                       BindingResult bindingResult)
    {
        System.out.println("addCart: " + dto.toString());
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), cartService.addCart(userId, dto));
    }
}
