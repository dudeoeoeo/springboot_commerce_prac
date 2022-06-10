package com.example.commerce.business.cart.service;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.dto.request.UpdateStock;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.dto.ResultResponse;

public interface CartService {

    void newCart(User user);
    ResultResponse addCart(Long userId, AddCartItem dto);
    void updateStock(Long userId, UpdateStock dto);
    Cart findByUser(User user);
    ResultResponse deleteCartItem(Long userId, Long optionId);
}
