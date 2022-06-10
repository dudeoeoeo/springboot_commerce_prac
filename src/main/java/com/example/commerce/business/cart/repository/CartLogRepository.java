package com.example.commerce.business.cart.repository;

import com.example.commerce.business.cart.domain.CartLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLogRepository extends JpaRepository<CartLog, Long> {
}
