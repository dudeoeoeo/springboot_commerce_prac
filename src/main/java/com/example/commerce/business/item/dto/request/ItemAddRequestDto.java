package com.example.commerce.business.item.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemAddRequestDto {

    private String name;

    private int price;

    private int stock;

    private int weight;

    private Long categoryId;

    private List<ItemOptionAddRequestDto> itemOptions = new ArrayList<>();

}
