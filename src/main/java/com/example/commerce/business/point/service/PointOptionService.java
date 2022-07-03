package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;

public interface PointOptionService {

    void newPointOption(Point point, int money, PointType type, PointUse use);
}
