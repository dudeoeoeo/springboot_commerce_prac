package com.example.commerce.business.cart.dao;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.dto.response.CartItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartItemDAO {

    Page<CartItemResponseDto> getCartItem(Pageable pageable, Cart cart);
}
