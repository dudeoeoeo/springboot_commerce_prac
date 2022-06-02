package com.example.commerce.business.item.dto.request;

import lombok.Getter;

@Getter
public class ItemOptionAddRequestDto {

    private int optionPrice;

    private int optionStock;

    private String optionSize;

    private String optionColor;

    private double optionWeight;
}
