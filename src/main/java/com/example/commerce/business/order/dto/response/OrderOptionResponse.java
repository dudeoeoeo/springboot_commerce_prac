package com.example.commerce.business.order.dto.response;

import com.example.commerce.business.order.domain.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderOptionResponse {

    private Long orderOptionId;
    private int price;
    private int stock;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime finishedDate;
}
