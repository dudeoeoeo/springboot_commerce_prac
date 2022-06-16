package com.example.commerce.business.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderListResponse {

    private OrderListDto orderList;
}
