package com.example.commerce.business.order.service;

import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.repository.OrderOptionRepository;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.service.PointService;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOptionServiceImpl implements OrderOptionService {

    private final OrderOptionRepository optionRepository;
    private final UserService userService;
    private final PointService pointService;

    @Override
    @Transactional
    public void newOrderOption(List<OrderOption> options) {
        optionRepository.saveAll(options);
    }

    public OrderOption findById(Long orderOptionId) {
        return optionRepository.findById(orderOptionId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 주문내역을 찾을 수 없습니다."));
    }

    @Transactional
    public ResultResponse updateTrackingNumber(Long orderOptionId, String trackingNumber) {
        final OrderOption orderOption = findById(orderOptionId);
        orderOption.updateTrackingNumber(trackingNumber);
        return ResultResponse.success("운송장 번호를 입력했습니다.");
    }

    @Override
    @Transactional
    public ResultResponse updateOrderOption(Long userId, Long optionId, OrderStatus orderStatus) {
        final User user = userService.findUserByUserId(userId);
        final OrderOption option = findById(optionId);
        if (!orderStatus.equals(OrderStatus.CHANGE)) {
            pointService.minusPoint(user, option.getPrice(), PointType.valueOf(String.valueOf(orderStatus)));
        }
        option.updateOrderStatus(orderStatus);
        return ResultResponse.success("주문상태를 변경했습니다.");
    }

}
