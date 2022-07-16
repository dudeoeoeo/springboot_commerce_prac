package com.example.commerce.business.point.service;

import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.dto.response.PointLogResponse;
import com.example.commerce.business.point.dto.response.PointResponse;
import com.example.commerce.business.point.repository.PointRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final UserService userService;
    private final PointOptionService optionService;

    @Override
    public PointResponse getPoint(Long userId) {
        final Point point = findByUser(userService.findUserByUserId(userId));
        return pointRepository.getPoints(point);
    }

    @Override
    public List<PointLogResponse> getPointLog(Long userId, int searchPage, int searchCount) {
        final Point point = findByUser(userService.findUserByUserId(userId));
        Pageable request = PageRequest.of(searchPage, searchCount, Sort.by("id").descending());
        return optionService.getAllPointOption(point, request);
    }

    @Override
    @Transactional
    public Point newPoint(User user) {
        final Point point = Point.newPoint(user);
        return pointRepository.save(point);
    }

    @Override
    @Transactional
    public ResultResponse plusPoint(User user, int point, PointType type) {
        final Point entity = findByUser(user);
        entity.plus(point);
        optionService.newPointOption(entity, point, type, PointUse.SAVE);
        return ResultResponse.success("포인트가 적립 되었습니다.");
    }

    @Override
    @Transactional
    public ResultResponse minusPoint(User user, int point, PointType type) {
        final Point entity = findByUser(user);
        entity.minus(point);
        optionService.newPointOption(entity, point, type, PointUse.USE);
        return ResultResponse.success("포인트가 사용 되었습니다.");
    }

    @Override
    public Point findByUser(User user) {
        final Optional<Point> point = pointRepository.findByUser(user);
        if (point.isEmpty()) {
            return newPoint(user);
        }
        return point.get();
    }
}
