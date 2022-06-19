package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderOption;

import java.util.List;

public interface OrderOptionService {

    void newOrderOption(List<OrderOption> options);
    OrderOption findById(Long orderOptionId);
}
