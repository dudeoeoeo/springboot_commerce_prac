package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.common.dto.ResultResponse;

public interface OrderService {

    ResultResponse newOrder(Long userId, OrderRequest dto);
    ResultResponse updateOrder(Long orderId, OrderStatus orderStatus);
    Orders findById(Long orderId);
}
