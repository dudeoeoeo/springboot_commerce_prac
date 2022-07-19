package com.example.commerce.business.promotion.repository;

import com.example.commerce.business.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
