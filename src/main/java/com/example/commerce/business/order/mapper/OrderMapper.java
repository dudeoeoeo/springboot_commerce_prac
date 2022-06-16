package com.example.commerce.business.order.mapper;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.dto.response.OrderDetailResponse;
import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.order.dto.response.OrderListResponse;
import com.example.commerce.business.order.dto.response.OrderOptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default public OrderDetailResponse toResponse(Orders order, List<OrderOption> orderOptions) {
        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .deliveryFee(order.getDeliveryFee())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .build();
//        final List<OrderOptionResponse> orderOptionResponse = orderOptions.stream()
//                .map(option -> toOrderOptionResponse(option))
//                .collect(Collectors.toList());
    }

    default public OrderOptionResponse toOrderOptionResponse(OrderOption orderOptions) {
        return OrderOptionResponse.builder()
                .orderOptionId(orderOptions.getId())
                .price(orderOptions.getPrice())
                .stock(orderOptions.getStock())
                .deliveryStatus(orderOptions.getDeliveryStatus())
                .finishedDate(orderOptions.getFinishedDate())
                .build();
    }
}
