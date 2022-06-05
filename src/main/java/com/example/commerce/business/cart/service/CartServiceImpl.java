package com.example.commerce.business.cart.service;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.repository.CartRepository;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemOptionService optionService;

    @Transactional
    public ResultResponse newCart(Long userId, AddCartItem dto) {
        final User user = userService.findUserByUserId(userId);
        final Optional<Cart> cart = findByUser(user);
        final Item item = itemService.findByItemId(dto.getItemId());
        final ItemOption option = optionService.findById(dto.getItemOptionId());
        if (cart.isPresent()) {
            final Cart newCart = Cart.createCart(user);
            newCart.addItem(item, option);
            cartRepository.save(newCart);
        } else {
            cart.get().addItem(item, option);
            cartRepository.save(cart.get());
        }
        return ResultResponse.success("장바구니에 상품을 추가했습니다.");
    }

    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }
}
