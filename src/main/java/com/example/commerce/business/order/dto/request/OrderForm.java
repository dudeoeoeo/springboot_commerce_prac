package com.example.commerce.business.order.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderForm {

    private Long itemId;

    private Long itemOptionId;

    private int stock;

    private int price;

}
