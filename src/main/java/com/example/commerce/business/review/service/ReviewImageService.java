package com.example.commerce.business.review.service;

import com.example.commerce.business.review.domain.ReviewImage;

import java.util.List;

public interface ReviewImageService {

    List<ReviewImage> createReviewImage(List<String> imagePaths);
}
