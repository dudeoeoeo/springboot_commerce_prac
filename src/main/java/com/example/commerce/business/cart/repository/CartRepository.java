package com.example.commerce.business.cart.repository;

import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
