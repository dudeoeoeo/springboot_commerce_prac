package com.example.commerce.business.order.dao;

import com.example.commerce.business.item.mapper.ItemMapper;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.dto.response.OrderListResponse;
import com.example.commerce.business.order.mapper.OrderMapper;
import com.example.commerce.business.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.commerce.business.order.domain.QOrderOption.orderOption;
import static com.example.commerce.business.order.domain.QOrders.orders;

@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private final JPAQueryFactory queryFactory;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public Page<OrderListResponse> getOrderList(Pageable pageable, User user) {
        final var content = queryFactory
                .select(orders, orderOption)
                .from(orders, orderOption)
                .leftJoin(orderOption.orders, orders)
                .where(orders.user.id.eq(user.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.id.desc())
                .fetch();

        return null;
    }
}
