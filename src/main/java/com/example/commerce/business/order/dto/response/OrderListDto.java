package com.example.commerce.business.order.dto.response;

import com.example.commerce.business.item.dto.response.ItemDetailResponseDto;
import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderListDto {

    private OrderDetailResponse order;
    private List<ItemDetailResponseDto> itemDetailResponseDtoList;
    private List<ItemOptionResponseDto> itemOptionResponseDtoList;
    private List<OrderOptionResponse> optionResponseList;

}
