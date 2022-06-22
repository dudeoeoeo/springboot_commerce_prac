package com.example.commerce.business.order.service;

import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.order.dto.response.OrderOptionDetailResponse;
import com.example.commerce.business.order.repository.OrderRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemOptionService optionService;
    private final OrderOptionService orderOptionService;

    @Transactional
    public ResultResponse newOrder(Long userId, OrderRequest dto) {
        final User user = userService.findUserByUserId(userId);
        List<OrderOption> orderOptions = new ArrayList<>();

        dto.getOrderForms().forEach(orderForm -> {
//            items.add(itemService.findByItemId(orderForm.getItemId()));
//            options.add(optionService.findById(orderForm.getItemOptionId()));
            orderOptions.add(OrderOption.createOrderOption(
                    orderForm,
                    itemService.findByItemId(orderForm.getItemId()),
                    optionService.findById(orderForm.getItemOptionId())));
        });

        final Orders saveOrder = orderRepository.save(Orders.createOrder(user, dto));

        orderOptions.forEach(orderOption -> orderOption.addOrder(saveOrder));
        orderOptionService.newOrderOption(orderOptions);

        return ResultResponse.success("주문이 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    public Page<OrderListDto> getOrderList(Long userId, int page, int size) {
        final User user = userService.findUserByUserId(userId);
        Pageable pageable = PageRequest.of(getSearchPage(page), size);
        return orderRepository.getOrderList(pageable, user);
    }

    public Orders findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 주문내역을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public OrderOptionDetailResponse getOrderOptionDetail(Long orderOptionId) {
        final OrderOption option = orderOptionService.findById(orderOptionId);
        return OrderOptionDetailResponse.createOptionDetail(option);
    }

    public int getSearchPage(int page) {
        return page - 1 < 0 ? page : page - 1;
    }

}
