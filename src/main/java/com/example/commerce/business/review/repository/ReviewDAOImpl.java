package com.example.commerce.business.review.repository;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.dto.response.OrderOptionDetailResponse;
import com.example.commerce.business.review.dto.response.ReviewListResponse;
import com.example.commerce.business.review.mapper.ReviewMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.stream.Collectors;

import static com.example.commerce.business.order.domain.QOrderOption.orderOption;
import static com.example.commerce.business.review.domain.QReview.review;

@RequiredArgsConstructor
public class ReviewDAOImpl implements ReviewDAO {

    private final JPAQueryFactory queryFactory;
    private final ReviewMapper reviewMapper;

    @Override
    public Page<ReviewListResponse> getReviewList(Pageable request, Item itemEntity) {
        final var contents = queryFactory
                .selectFrom(review)
                .distinct()
                .where(
                        review.item.eq(itemEntity),
                        review.deleteYn.eq(0),
                        orderOption.orderStatus.eq(OrderStatus.ORDER)
                )
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .orderBy(review.id.desc())
                .fetch()
                .stream()
                .map(review ->
                    ReviewListResponse.builder()
                        .review(reviewMapper.toReviewResponse(review))
                        .orderOptionDetail(OrderOptionDetailResponse.createOptionDetail(review.getOrderOption()))
                        .build())
                .collect(Collectors.toList());

        final JPAQuery<?> count = queryFactory
                .selectFrom(review)
                .distinct()
                .where(
                        review.item.eq(itemEntity),
                        review.deleteYn.eq(0),
                        orderOption.orderStatus.eq(OrderStatus.ORDER)
                );

        return PageableExecutionUtils.getPage(contents, request, count::fetchCount);
    }
}
