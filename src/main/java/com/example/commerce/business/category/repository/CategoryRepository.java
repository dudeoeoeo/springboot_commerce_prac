package com.example.commerce.business.category.repository;

import com.example.commerce.business.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByDeleteYnOrderByIdDesc(int deleteYn);
}
