package com.example.commerce.business.item.dto.request;

import lombok.Getter;


@Getter
public class ItemOptionAddRequestDto {

    private int optionPrice;

    private int optionStock;

    private int optionSize;

    private int optionColor;

    private int optionWeight;
}
