package com.example.commerce.business.cart.service;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.dto.ResultResponse;

import java.util.Optional;

public interface CartService {

    ResultResponse newCart(Long userId, AddCartItem dto);
    Optional<Cart> findByUser(User user);
}
