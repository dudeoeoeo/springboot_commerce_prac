package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.dto.response.PointLogResponse;
import com.example.commerce.business.point.dto.response.PointResponse;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.dto.ResultResponse;

import java.util.List;

public interface PointService {

    PointResponse getPoint(Long userId);
    List<PointLogResponse> getPointLog(Long userId, int searchPage, int searchCount);
    Point newPoint(User user);
    void usePoint(User user, int point);
    ResultResponse plusPoint(User user, int point, PointType type);
    ResultResponse minusPoint(User user, int point, PointType type);
    Point findByUser(User user);
    void findAllExpiredPoint();
}
