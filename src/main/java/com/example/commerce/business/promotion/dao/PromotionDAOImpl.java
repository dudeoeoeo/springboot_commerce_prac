package com.example.commerce.business.promotion.dao;

import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.dto.response.PromotionItemResponse;
import com.example.commerce.business.promotion.dto.response.PromotionResponse;
import com.example.commerce.business.promotion.mapper.PromotionMapper;
import com.example.commerce.business.user.domain.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.commerce.business.promotion.domain.QPromotion.promotion;
import static com.example.commerce.business.promotion.domain.QPromotionLog.promotionLog;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class PromotionDAOImpl implements PromotionDAO {

    private final JPAQueryFactory jpaQueryFactory;
    private final PromotionMapper promotionMapper;

    @Override
    public Page<PromotionItemResponse> getPromotionList(Pageable pageable, LocalDate today) {
        final List<PromotionItemResponse> contents = jpaQueryFactory
                .selectFrom(
                        promotion
                )
                .where(
                        promotion.startDate.loe(today),
                        promotion.endDate.goe(today)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(p -> promotionMapper.toPromotionItemResponse(p))
                .collect(Collectors.toList());

        final JPAQuery<Promotion> count = jpaQueryFactory
                .selectFrom(
                        promotion
                )
                .where(
                        promotion.startDate.loe(today),
                        promotion.endDate.goe(today)
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchCount);
    }

    @Override
    public PromotionResponse getPromotionLogDetail(User user, Promotion entity) {
        final PromotionResponse contents = jpaQueryFactory
                .from(
                        promotion,
                        promotionLog
                )
                .where(
                        promotionLog.promotion.eq(promotion),
                        promotionLog.user.eq(user)
                )
                .transform(groupBy(promotion).as(list(promotionLog)))
                .entrySet()
                .stream()
                .map(entry -> promotionMapper.toPromotionLogResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList())
                .get(0);

        return contents;
    }

    @Override
    public Page<PromotionResponse> getPromotionLog(Pageable pageable, User user) {

        final List<PromotionResponse> contents = jpaQueryFactory
                .from(
                        promotion,
                        promotionLog
                )
                .where(promotionLog.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(promotionLog.id.desc())
                .transform(groupBy(promotion).as(list(promotionLog)))
                .entrySet()
                .stream()
                .map(entry -> promotionMapper.toPromotionLogResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        final JPAQuery<?> count = jpaQueryFactory
                .from(
                        promotion,
                        promotionLog
                )
                .leftJoin(promotionLog.promotion, promotion)
                .where(promotionLog.user.eq(user));
        return PageableExecutionUtils.getPage(contents, pageable, count::fetchCount);
    }
}
