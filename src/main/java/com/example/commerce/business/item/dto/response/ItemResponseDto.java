package com.example.commerce.business.item.dto.response;

import com.example.commerce.business.item.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ItemResponseDto {

    private Long id;
    private String name;
    private int price;
    private int stock;
    private int weight;
    private ItemStatus itemStatus;
    private LocalDateTime createDate;
    /**
     * TODO: ItemImage Response Dto, ItemOption Response Dto 추가
     */
    private List<String> itemImages = new ArrayList<>();

}
