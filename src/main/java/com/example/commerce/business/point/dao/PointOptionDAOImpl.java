package com.example.commerce.business.point.dao;

import com.example.commerce.business.point.PointMapper;
import com.example.commerce.business.point.domain.*;
import com.example.commerce.business.point.dto.response.PointResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.commerce.business.point.domain.QPoint.point1;
import static com.example.commerce.business.point.domain.QPointOption.pointOption;

@RequiredArgsConstructor
public class PointOptionDAOImpl implements PointOptionDAO {

    private final JPAQueryFactory queryFactory;
    private final PointMapper pointMapper;

    /**
     * TODO: Group by ?
     */
    @Override
    public PointResponse getPoints(Point entity) {
        final List<PointOption> list = queryFactory
                .selectFrom(pointOption)
                .leftJoin(pointOption.point, point1)
                .where(
                        pointOption.point.eq(entity)
                )
                .fetch();

        return pointMapper.toPointResponse(entity, list);
    }
}
