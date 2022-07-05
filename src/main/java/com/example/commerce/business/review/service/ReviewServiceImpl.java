package com.example.commerce.business.review.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.service.OrderOptionService;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.service.PointService;
import com.example.commerce.business.review.domain.Review;
import com.example.commerce.business.review.domain.ReviewImage;
import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.business.review.dto.response.ReviewListResponse;
import com.example.commerce.business.review.repository.ReviewRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private UserService userService;
    private ItemService itemService;
    private OrderOptionService orderOptionService;
    private AmazonS3Service amazonS3Service;
    private final PointService pointService;
    private final String FOLDER = "review";

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewListResponse> getReviewList(Long itemId, int page, int count) {
        final Item item = itemService.findByItemId(itemId);
        Pageable request = PageRequest.of(page, count);
        return reviewRepository.getReviewList(request, item);
    }

    @Override
    @Transactional
    public ResultResponse addReview(Long userId, AddReviewRequest dto, List<MultipartFile> files) {
        final User user = userService.findUserByUserId(userId);
        final Item item = itemService.findByItemId(dto.getItemId());
        final OrderOption orderOption = orderOptionService.findById(dto.getOrderOptionId());

        if (files.isEmpty() == false) {
            final List<ReviewImage> reviewImageList =
                    reviewImageService.createReviewImage(amazonS3Service.uploadFiles(files, FOLDER));
            final Review review = Review.createReview(user, item, orderOption, dto, reviewImageList);
            reviewRepository.save(review);
            pointService.plusPoint(user, 100, PointType.PHOTO_REVIEW);
        } else {
            final Review review = Review.createReview(user, item, orderOption, dto, null);
            reviewRepository.save(review);
            pointService.minusPoint(user, 50, PointType.TEXT_REVIEW);
        }

        return ResultResponse.success("상품 리뷰를 추가했습니다.");
    }

    @Override
    @Transactional
    public ResultResponse deleteReview(Long userId, Long reviewId) {
        final User user = userService.findUserByUserId(userId);
        final Review review = findById(reviewId);
        review.deleteReview(user);
        return ResultResponse.success("상품 리뷰를 삭제했습니다.");
    }

    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }
}
