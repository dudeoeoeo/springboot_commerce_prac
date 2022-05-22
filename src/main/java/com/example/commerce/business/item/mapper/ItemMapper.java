package com.example.commerce.business.item.mapper;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    default public ItemResponseDto toResponse(Item item, List<ItemImage> itemImages) {
        return ItemResponseDto
                .builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .weight(item.getWeight())
                .itemStatus(item.getItemStatus())
                .itemImages(itemImages.stream().map(e -> e.getImageUrl()).collect(Collectors.toList()))
                .build();
    }
}
