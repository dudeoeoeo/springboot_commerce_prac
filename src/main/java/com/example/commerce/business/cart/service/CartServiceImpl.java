package com.example.commerce.business.cart.service;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.domain.CartLog;
import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.cart.dto.request.UpdateStock;
import com.example.commerce.business.cart.dto.response.CartItemResponseDto;
import com.example.commerce.business.cart.repository.CartItemRepository;
import com.example.commerce.business.cart.repository.CartLogRepository;
import com.example.commerce.business.cart.repository.CartRepository;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartLogRepository cartLogRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemOptionService optionService;

    @Transactional(readOnly = true)
    public Page<CartItemResponseDto> getCartItem(Long userId, int page, int count) {
        final User user = userService.findUserByUserId(userId);
        final Cart cart = findByUser(user);
        System.out.println("userId: " + userId + ", page: " + page + ", count: " + count);
        return cartItemRepository.getCartItem(PageRequest.of(getSearchPage(page), count), cart);
    }

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

        if (dto.getOptionStock() > option.getOptionStock())
            return ResultResponse.success("상품 최대 수량은 " + option.getOptionStock() + "개 입니다.");

        // 이미 추가했던 상품이므로 수량만 변경
        if (saveCartItem != null) {
            if (saveCartItem.getStock() + dto.getOptionStock() > option.getOptionStock())
                return ResultResponse.success("상품 최대 수량은 " + option.getOptionStock() + "개 입니다.");
            saveCartItem.addStock(dto.getOptionStock());
            return ResultResponse.success("상품을 장바구니에 담았습니다. 이미 담으신 상품이 있습니다. 장바구니로 이동하시겠습니까 ?");
        }

        final CartItem cartItem = CartItem.createCartItem(cart, item, option, dto.getOptionStock());
        cartItemRepository.save(cartItem);

        return ResultResponse.success("장바구니에 상품을 추가했습니다.");
    }

    @Transactional
    public void updateStock(Long userId, UpdateStock dto) {
        final Cart cart = findByUser(userService.findUserByUserId(userId));
        final CartItem cartItem = cartItemRepository.findByCartAndItemOption(cart, optionService.findById(dto.getItemOptionId()));
        cartItem.updateStock(dto.getStock());
        cartItemRepository.save(cartItem);
    }

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalThreadStateException("해당 장바구니가 존재하지 않습니다."));
    }

    @Transactional
    public ResultResponse deleteCartItem(Long userId, Long optionId) {
        final User user = userService.findUserByUserId(userId);
        final ItemOption option = optionService.findById(optionId);
        final CartLog cartLog = CartLog.deleteCartItem(user, option);
        cartLogRepository.save(cartLog);
        return ResultResponse.success("장바구니에서 상품을 삭제했습니다.");
    }

    public int getSearchPage(int page) {
        return page - 1 < 0 ? page : page - 1;
    }
}
