package com.example.commerce.business.review.controller;

import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.business.review.service.ReviewService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController extends CommonUtil {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public SuccessResponse addReview(HttpServletRequest request,
                                     @Valid @RequestPart("review_request") final AddReviewRequest dto,
                                     @RequestPart("review_image") final List<MultipartFile> uploadFiles)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), reviewService.addReview(userId, dto, uploadFiles));
    }
}
