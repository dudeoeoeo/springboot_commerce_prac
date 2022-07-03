package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.dto.ResultResponse;

public interface PointService {

    void newPoint(User user);
    ResultResponse plusPoint(User user, int point, PointType type);
    ResultResponse minusPoint(User user, int point, PointType type);
}
