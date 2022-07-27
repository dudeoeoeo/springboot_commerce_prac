package com.example.commerce.business.promotion.repository;

import com.example.commerce.business.promotion.domain.PromotionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionLogRepository extends JpaRepository<PromotionLog, Long> {
}
