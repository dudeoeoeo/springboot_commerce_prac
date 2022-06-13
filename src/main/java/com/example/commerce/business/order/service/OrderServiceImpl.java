package com.example.commerce.business.order.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.order.domain.Order;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.order.repository.OrderRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
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
        List<Item> items = new ArrayList<>();
        List<ItemOption> options = new ArrayList<>();
        List<OrderOption> orderOptions = new ArrayList<>();

        dto.getOrderForms().forEach(orderForm -> {
            items.add(itemService.findByItemId(orderForm.getItemId()));
            options.add(optionService.findById(orderForm.getItemOptionId()));
            orderOptions.add(OrderOption.createOrderOption(orderForm, dto.getDeliveryFee(), dto.getPaymentStatus()));
        });

        final Order saveOrder = orderRepository.save(Order.createOrder(user, items, options));

        orderOptions.forEach(orderOption -> orderOption.addOrder(saveOrder));
        orderOptionService.newOrderOption(orderOptions);

        return ResultResponse.success("주문이 완료되었습니다.");
    }
}