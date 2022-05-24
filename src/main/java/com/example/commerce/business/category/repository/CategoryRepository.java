package com.example.commerce.business.category.repository;

import com.example.commerce.business.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
