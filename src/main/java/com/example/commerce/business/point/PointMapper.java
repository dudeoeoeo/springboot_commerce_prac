package com.example.commerce.business.point;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointOption;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.dto.response.PointResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {

    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    default public PointResponse toPointResponse(Point point, List<PointOption> pointOptions) {
        int orderPoint = 0, reviewPoint = 0, eventPoint = 0, usePoint = 0;
        for (int i = 0; i < pointOptions.size(); i++) {
            final PointType type = pointOptions.get(i).getPointType();
            final int pointMoney = pointOptions.get(i).getMoney();

            if (pointOptions.get(i).getPointUse().equals(PointUse.SAVE)) {
                if (type.equals(PointType.ORDER)) {
                    orderPoint += pointMoney;
                } else if (type.equals(PointType.TEXT_REVIEW) || type.equals(PointType.PHOTO_REVIEW)) {
                    reviewPoint += pointMoney;
                } else {
                    eventPoint += pointMoney;
                }
            } else {
                usePoint += pointMoney;
            }
        }
        return PointResponse.builder()
                .point(point.getPoint())
                .orderPoint(orderPoint)
                .reviewPoint(reviewPoint)
                .eventPoint(eventPoint)
                .usePoint(usePoint)
                .build();
    }
}
