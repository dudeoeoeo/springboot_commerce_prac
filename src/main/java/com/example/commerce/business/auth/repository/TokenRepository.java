package com.example.commerce.business.auth.repository;

import com.example.commerce.business.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
