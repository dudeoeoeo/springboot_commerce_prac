package com.example.commerce.business.review.service;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.service.OrderOptionService;
import com.example.commerce.business.review.domain.Review;
import com.example.commerce.business.review.domain.ReviewImage;
import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.business.review.repository.ReviewRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final String FOLDER = "review";

    @Override
    @Transactional
    public ResultResponse addReview(Long userId, AddReviewRequest dto, List<MultipartFile> files) {
        final User user = userService.findUserByUserId(userId);
        final Item item = itemService.findByItemId(dto.getItemId());
        final OrderOption orderOption = orderOptionService.findById(dto.getOrderOptionId());
        final List<ReviewImage> reviewImageList =
                reviewImageService.createReviewImage(amazonS3Service.uploadFiles(files, FOLDER));

        final Review review = Review.createReview(user, item, orderOption, dto, reviewImageList);
        reviewRepository.save(review);
        return ResultResponse.success("상품 리뷰를 추가했습니다.");
    }
}
