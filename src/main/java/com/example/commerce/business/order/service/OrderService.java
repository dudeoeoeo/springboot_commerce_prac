package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.dto.request.OrderPromotionRequest;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.order.dto.response.OrderOptionDetailResponse;
import com.example.commerce.common.dto.ResultResponse;
import org.springframework.data.domain.Page;

public interface OrderService {

    ResultResponse newOrder(Long userId, OrderRequest dto);
    Page<OrderListDto> getOrderList(Long userId, int page, int size);
    Orders findById(Long orderId);
    OrderOptionDetailResponse getOrderOptionDetail(Long orderOptionId);
    ResultResponse buyPromotion(Long userId, OrderPromotionRequest dto);
}
