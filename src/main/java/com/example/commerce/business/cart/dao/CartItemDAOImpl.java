package com.example.commerce.business.cart.dao;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.domain.QCart;
import com.example.commerce.business.cart.domain.QCartItem;
import com.example.commerce.business.cart.dto.response.CartItemResponseDto;
import com.example.commerce.business.item.domain.QItem;
import com.example.commerce.business.item.domain.QItemOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.commerce.business.cart.domain.QCart.cart;
import static com.example.commerce.business.cart.domain.QCartItem.cartItem;
import static com.example.commerce.business.item.domain.QItem.item;
import static com.example.commerce.business.item.domain.QItemOption.itemOption;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class CartItemDAOImpl implements CartItemDAO {

    private final JPAQueryFactory queryFactory;

    public CartItemResponseDto getCartItem(Pageable pageable, Cart cartEntity) {
        final List<CartItem> fetch = queryFactory
                .selectFrom(cartItem)
                .leftJoin(cartItem.cart, cart)
                .fetchJoin()
                .where(cart.id.eq(cartEntity.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(cartItem.id.desc())
                .fetch();

        return null;
    }
}
