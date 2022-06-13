package com.example.commerce.business.order.repository;

import com.example.commerce.business.order.domain.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOptionRepository extends JpaRepository<OrderOption, Long> {
}
