package com.example.commerce.business.order.dto.response;

import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import com.example.commerce.business.order.domain.OrderOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class OrderOptionDetailResponse {

    ItemResponseDto item;
    ItemOptionResponseDto itemOption;
    OrderOptionResponse orderOption;

    public static OrderOptionDetailResponse createOptionDetail(OrderOption option) {
        return OrderOptionDetailResponse.builder()
                .item(ItemResponseDto.builder()
                        .id(option.getItem().getId())
                        .name(option.getItem().getName())
                        .itemStatus(option.getItem().getItemStatus())
                        .createDate(option.getItem().getCreateDt())
                        .itemImages(option.getItem().getItemImages().stream().map(e -> e.getImageUrl()).collect(Collectors.toList()))
                        .build())
                .itemOption(ItemOptionResponseDto.builder()
                        .id(option.getItemOption().getId())
                        .optionPrice(option.getItemOption().getOptionPrice())
                        .optionSize(option.getItemOption().getOptionSize())
                        .optionColor(option.getItemOption().getOptionColor())
                        .optionWeight(option.getItemOption().getOptionWeight())
                        .build())
                .orderOption(OrderOptionResponse.builder()
                        .orderOptionId(option.getId())
                        .price(option.getPrice())
                        .stock(option.getStock())
                        .trackingNumber(option.getTrackingNumber())
                        .deliveryStatus(option.getDeliveryStatus())
                        .finishedDate(option.getFinishedDate())
                        .build())
                .build();
    }
}
