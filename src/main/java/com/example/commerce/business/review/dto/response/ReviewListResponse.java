package com.example.commerce.business.review.dto.response;

import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ReviewListResponse {

    private ItemResponseDto item;
    private ItemOptionResponseDto itemOption;
}
