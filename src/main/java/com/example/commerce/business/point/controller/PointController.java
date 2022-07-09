package com.example.commerce.business.point.controller;

import com.example.commerce.business.point.service.PointService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/point")
@RequiredArgsConstructor
public class PointController extends CommonUtil {

    private final PointService pointService;

    @GetMapping
    public SuccessResponse getPoint(HttpServletRequest request)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), pointService.getPoint(userId));
    }

    @GetMapping("/log")
    public SuccessResponse getPointLog(HttpServletRequest request,
                                       @RequestParam("searchPage") int searchPage,
                                       @RequestParam("searchCount") int searchCount)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), pointService.getPointLog(userId, getSearchPage(searchPage), searchCount));
    }

}
