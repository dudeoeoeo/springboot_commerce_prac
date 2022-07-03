package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointOption;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.repository.PointOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointOptionServiceImpl implements PointOptionService {

    private final PointOptionRepository pointOptionRepository;

    @Override
    @Transactional
    public void newPointOption(Point point, int money, PointType type, PointUse use) {
        final PointOption pointOption = PointOption.newPointOption(point, money, type, use);
        pointOptionRepository.save(pointOption);
    }
}
