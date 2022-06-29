package com.example.commerce.business.review.repository;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.review.dto.response.ReviewListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewDAO {

    Page<ReviewListResponse> getReviewList(Pageable request, Item item);
}
