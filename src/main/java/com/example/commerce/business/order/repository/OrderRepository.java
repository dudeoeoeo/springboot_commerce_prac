package com.example.commerce.business.order.repository;

import com.example.commerce.business.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
