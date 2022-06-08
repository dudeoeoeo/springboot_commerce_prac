package com.example.commerce.business.cart.service;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.dto.request.UpdateStock;
import com.example.commerce.business.cart.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemOptionService optionService;

    // 회원가입과 동시에 장바구니 생성
    @Transactional
    public void newCart(User user) {
        final Cart newCart = Cart.createCart(user);
        cartRepository.save(newCart);
    }

    @Transactional
    public ResultResponse addCart(Long userId, AddCartItem dto) {
        final User user = userService.findUserByUserId(userId);
        final Cart cart = findByUser(user);
        final Item item = itemService.findByItemId(dto.getItemId());
        final ItemOption option = optionService.findById(dto.getItemOptionId());

        final CartItem saveCartItem = cartItemRepository.findByCartAndItemAndItemOption(cart, item, option);

        // 이미 추가했던 상품이므로 수량만 변경
        if (saveCartItem != null) {
            saveCartItem.updateStock(dto.getOptionStock());
            return ResultResponse.success("상품을 장바구니에 담았습니다. 이미 담으신 상품이 있습니다. 장바구니로 이동하시겠습니까 ?");
        }

        final CartItem cartItem = CartItem.createCartItem(cart, item, option, dto.getOptionStock());
        cartItemRepository.save(cartItem);

        return ResultResponse.success("장바구니에 상품을 추가했습니다.");
    }

    @Transactional
    public ResultResponse updateStock(Long userId, UpdateStock dto) {
        final Cart cart = findByUser(userService.findUserByUserId(userId));

        return null;
    }

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalThreadStateException("해당 장바구니가 존재하지 않습니다."));
    }
}
