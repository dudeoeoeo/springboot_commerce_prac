package com.example.commerce.business.order.dao;

import com.example.commerce.business.item.mapper.ItemMapper;
import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.order.mapper.OrderMapper;
import com.example.commerce.business.user.domain.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.commerce.business.order.domain.QOrderOption.orderOption;
import static com.example.commerce.business.order.domain.QOrders.orders;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private final JPAQueryFactory queryFactory;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public Page<OrderListDto> getOrderList(Pageable pageable, User user) {
        final List<OrderListDto> contents = queryFactory
                .from(orders, orderOption)
                .leftJoin(orderOption.orders, orders)
                .where(orders.user.id.eq(user.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.id.desc())
                .transform(groupBy(orders).as(list(orderOption)))
                .entrySet()
                .stream().map(entry -> OrderListDto.builder()
                        .order(orderMapper.toOrderResponse(entry.getKey()))
                        .optionResponseList(entry.getValue().stream().map(orderMapper::toOrderOptionResponse).collect(Collectors.toList()))
                        .itemResponseDtoList(itemMapper.toItemResponse(entry.getKey().getItem()))
                        .itemOptionResponseDtoList(itemMapper.toOptionResponse(entry.getKey().getItemOption()))
                        .build())
                .collect(Collectors.toList());

        final JPAQuery<?> count = queryFactory
                .from(orders, orderOption)
                .leftJoin(orderOption.orders, orders)
                .where(orders.user.id.eq(user.getId()));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchCount);
    }
}
