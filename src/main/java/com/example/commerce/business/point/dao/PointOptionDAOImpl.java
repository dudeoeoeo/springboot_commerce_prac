package com.example.commerce.business.point.dao;

import com.example.commerce.business.point.mapper.PointMapper;
import com.example.commerce.business.point.domain.*;
import com.example.commerce.business.point.dto.response.PointResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void bulkUpdateExpiredPoint(List<Long> ids) {
        // bulk update
        queryFactory
                .update(pointOption)
                .set(pointOption.deleteDt, LocalDateTime.now().withNano(0))
                .set(pointOption.deleteYn, 1)
                .set(pointOption.pointType, PointType.EXPIRED)
                .where(pointOption.id.in(ids))
                .execute();
    }

    @Override
    public List<Long> findAllExpiredPointIds(LocalDateTime expireDate) {
        final List<Long> optionIds = queryFactory
                .selectFrom(pointOption)
                .where(
                        pointOption.createDt.loe(expireDate),
                        pointOption.deleteYn.eq(0)
                )
                .fetch()
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        return optionIds;
    }
}
