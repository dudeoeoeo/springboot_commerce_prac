package com.example.commerce.business.order.dto.request;

import com.example.commerce.business.order.domain.PaymentStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private List<OrderForm> orderForms;

    private Long couponId;

    private int totalPrice;

    private int deliveryFee;

    private PaymentStatus paymentStatus;

}
