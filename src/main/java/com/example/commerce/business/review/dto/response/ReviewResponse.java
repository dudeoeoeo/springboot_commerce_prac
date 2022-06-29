package com.example.commerce.business.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ReviewResponse {

    private Long id;

    private String content;

    private double star;

    private List<String> reviewImages;
}
