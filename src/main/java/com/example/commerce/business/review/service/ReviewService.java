package com.example.commerce.business.review.service;

import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.common.dto.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    ResultResponse addReview(Long userId, AddReviewRequest dto, List<MultipartFile> files);
}
