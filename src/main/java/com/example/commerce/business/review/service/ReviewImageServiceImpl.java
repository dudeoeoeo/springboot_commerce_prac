package com.example.commerce.business.review.service;

import com.example.commerce.business.review.domain.ReviewImage;
import com.example.commerce.business.review.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewImageServiceImpl implements ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    @Override
    @Transactional
    public List<ReviewImage> createReviewImage(List<String> imagePaths) {
        AtomicInteger idx = new AtomicInteger();
        final List<ReviewImage> reviewImages = imagePaths
                .stream()
                .map(path -> ReviewImage.newReviewImage(path, idx.getAndIncrement() + 1))
                .collect(Collectors.toList());

        return reviewImageRepository.saveAll(reviewImages);
    }
}
