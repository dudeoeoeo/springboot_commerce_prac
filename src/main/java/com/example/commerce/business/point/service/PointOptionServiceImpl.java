package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointOption;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.dto.response.PointLogResponse;
import com.example.commerce.business.point.mapper.PointOptionMapper;
import com.example.commerce.business.point.repository.PointOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointOptionServiceImpl implements PointOptionService {

    private final PointOptionRepository pointOptionRepository;
    private final PointOptionMapper optionMapper;

    @Override
    public List<PointLogResponse> getAllPointOption(Point point, Pageable pageable) {
        final Optional<List<PointOption>> pointOptions = pointOptionRepository.findAllByPoint(point, pageable);
        if (pointOptions.isEmpty())
            return null;

        return pointOptions.get()
                .stream()
                .map(e -> optionMapper.toPointLog(e))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void newPointOption(Point point, int money, PointType type, PointUse use) {
        final PointOption pointOption = PointOption.newPointOption(point, money, type, use);
        pointOptionRepository.save(pointOption);
    }
}
