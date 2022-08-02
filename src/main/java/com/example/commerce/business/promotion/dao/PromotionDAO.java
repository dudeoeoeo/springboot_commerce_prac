package com.example.commerce.business.promotion.dao;

import com.example.commerce.business.promotion.dto.response.PromotionResponse;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionDAO {

    Page<PromotionResponse> getPromotionLog(Pageable pageable, User user);
}
