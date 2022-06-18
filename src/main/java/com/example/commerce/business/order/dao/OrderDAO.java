package com.example.commerce.business.order.dao;

import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDAO {

    Page<OrderListDto> getOrderList(Pageable pageable, User user);
}
