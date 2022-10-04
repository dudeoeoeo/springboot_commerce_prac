package com.example.commerce.business.point.dao;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.dto.response.PointResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PointOptionDAO {

    PointResponse getPoints(Point point);

    void bulkUpdateExpiredPoint(List<Long> ids);

    List<Long> findAllExpiredPointIds(LocalDateTime expireDate);
}
