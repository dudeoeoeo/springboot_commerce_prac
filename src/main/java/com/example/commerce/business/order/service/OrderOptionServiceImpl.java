package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.repository.OrderOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOptionServiceImpl implements OrderOptionService {

    private final OrderOptionRepository optionRepository;

    @Override
    public void newOrderOption(List<OrderOption> options) {
        optionRepository.saveAll(options);
    }
}
