package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.dto.response.OrderOptionDetailResponse;
import com.example.commerce.business.order.repository.OrderOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOptionServiceImpl implements OrderOptionService {

    private final OrderOptionRepository optionRepository;

    @Override
    public void newOrderOption(List<OrderOption> options) {
        optionRepository.saveAll(options);
    }

    public OrderOption findById(Long orderOptionId) {
        return optionRepository.findById(orderOptionId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 주문내역을 찾을 수 없습니다."));
    }
}
