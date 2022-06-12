package com.example.commerce.business.cart.mapper;

import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.dto.response.CartItemResponseDto;
import com.example.commerce.business.item.dto.response.ItemOptionResponseDto;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    default public CartItemResponseDto toResponse(CartItem cartItem) {
        final ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .id(cartItem.getItem().getId())
                .name(cartItem.getItem().getName())
                .itemStatus(cartItem.getItem().getItemStatus())
                .createDate(cartItem.getCreateDt())
                .itemImages(cartItem.getItem().getItemImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList()))
                .build();
        final ItemOptionResponseDto itemOptionResponseDto = ItemOptionResponseDto.builder()
                .id(cartItem.getItemOption().getId())
                .optionPrice(cartItem.getItemOption().getOptionPrice())
                .optionStock(cartItem.getItemOption().getOptionStock())
                .optionSize(cartItem.getItemOption().getOptionSize())
                .optionColor(cartItem.getItemOption().getOptionColor())
                .optionWeight(cartItem.getItemOption().getOptionWeight())
                .build();
        return CartItemResponseDto.builder()
                .item(itemResponseDto)
                .itemOption(itemOptionResponseDto)
                .build();
    }

}
