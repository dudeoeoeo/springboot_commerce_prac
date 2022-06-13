package com.example.commerce.business.order.service;

import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.common.dto.ResultResponse;

public interface OrderService {

    ResultResponse newOrder(Long userId, OrderRequest dto);
}
