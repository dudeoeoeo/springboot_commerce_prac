package com.example.commerce.business.promotion.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class AddPromotion {

    private Long itemOptionId;

    private double discountPercent;

    private int stock;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
