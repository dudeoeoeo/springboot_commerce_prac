package com.example.commerce.business.order.dto.response;

import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderDetailResponse {

    private Long orderId;
    private int deliveryFee;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
}
