package com.example.commerce.business.review.mapper;

import com.example.commerce.business.review.domain.Review;
import com.example.commerce.business.review.dto.response.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    default public ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .reviewImages(
                        review
                            .getReviewImages()
                            .stream()
                            .map(i -> i.getImageUrl()).collect(Collectors.toList())
                )
                .build();
    }
}
