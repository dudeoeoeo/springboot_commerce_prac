package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointOption;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.dto.response.PointLogResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PointOptionService {

    List<PointLogResponse> getAllPointOption(Point point, Pageable pageable);
    void newPointOption(Point point, int money, PointType type, PointUse use);
}
