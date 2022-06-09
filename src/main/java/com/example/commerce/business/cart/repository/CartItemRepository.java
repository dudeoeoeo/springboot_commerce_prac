package com.example.commerce.business.cart.repository;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndItemAndItemOption(Cart cart, Item item, ItemOption itemOption);
    CartItem findByCartAndItemOption(Cart cart, ItemOption itemOption);
}
