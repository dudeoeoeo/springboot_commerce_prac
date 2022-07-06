package com.example.commerce.business.point.dao;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.dto.response.PointResponse;

public interface PointOptionDAO {

    PointResponse getPoints(Point point);
}
