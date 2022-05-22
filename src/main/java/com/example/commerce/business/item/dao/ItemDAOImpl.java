package com.example.commerce.business.item.dao;


import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.QItem;
import com.example.commerce.business.item.domain.QItemImage;
import com.example.commerce.business.item.dto.response.ItemResponseDto;
import com.example.commerce.business.item.mapper.ItemMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.commerce.business.item.domain.QItem.item;
import static com.example.commerce.business.item.domain.QItemImage.itemImage;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ItemDAOImpl implements ItemDAO {

    private final JPAQueryFactory queryFactory;
    private final ItemMapper itemMapper;

    /**
     * TODO: 검색 조건 추가
     */
    @Override
    public Page<ItemResponseDto> getItemList(Pageable pageable) {
        final List<ItemResponseDto> contents = queryFactory
                .from(item)
                .leftJoin(item.itemImages, itemImage)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.id.desc(), itemImage.orderedSeq.asc())
                .transform(groupBy(item).as(list(itemImage)))
                .entrySet()
                .stream()
                .map(entry -> itemMapper.toResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        final JPAQuery<?> count = queryFactory
                .from(item)
                .leftJoin(item.itemImages, itemImage);
        return PageableExecutionUtils.getPage(contents, pageable, count::fetchCount);
    }
}
