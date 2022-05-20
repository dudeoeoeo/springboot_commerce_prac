package com.example.commerce.business.item.dto.request;

import lombok.Getter;

@Getter
public class ItemUpdateRequestDto {

    private String name;

    private int price;

    private int stock;

    private int weight;

}
