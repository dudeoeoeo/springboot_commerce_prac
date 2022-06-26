package com.example.commerce.business.review.service;

import com.example.commerce.business.review.domain.Review;
import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.business.review.dto.response.ReviewListResponse;
import com.example.commerce.common.dto.ResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    Page<ReviewListResponse> getReviewList(Long itemId, int page, int count);
    ResultResponse addReview(Long userId, AddReviewRequest dto, List<MultipartFile> files);
    ResultResponse deleteReview(Long userId, Long reviewId);
    Review findById(Long reviewId);
}
