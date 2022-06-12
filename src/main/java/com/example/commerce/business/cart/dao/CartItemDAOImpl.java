package com.example.commerce.business.cart.dao;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.domain.QCart;
import com.example.commerce.business.cart.domain.QCartItem;
import com.example.commerce.business.cart.dto.response.CartItemResponseDto;
import com.example.commerce.business.cart.mapper.CartItemMapper;
import com.example.commerce.business.item.domain.QItem;
import com.example.commerce.business.item.domain.QItemOption;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.commerce.business.cart.domain.QCart.cart;
import static com.example.commerce.business.cart.domain.QCartItem.cartItem;
import static com.example.commerce.business.item.domain.QItem.item;
import static com.example.commerce.business.item.domain.QItemOption.itemOption;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class CartItemDAOImpl implements CartItemDAO {

    private final JPAQueryFactory queryFactory;
    private final CartItemMapper cartItemMapper;

    public Page<CartItemResponseDto> getCartItem(Pageable pageable, Cart cartEntity) {
        final List<CartItemResponseDto> content = queryFactory
                .selectFrom(cartItem)
                .leftJoin(cartItem.cart, cart)
                .fetchJoin()
                .where(cart.id.eq(cartEntity.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(cartItem.id.desc())
                .fetch()
                .stream()
                .map(e -> cartItemMapper.toResponse(e))
                .collect(Collectors.toList());

        final JPAQuery<CartItem> count = queryFactory
                .selectFrom(cartItem)
                .leftJoin(cartItem.cart, cart)
                .fetchJoin()
                .where(cart.id.eq(cartEntity.getId()));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }
}
