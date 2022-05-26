package com.example.commerce.business.category.service;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.dto.request.CategoryAddRequestDto;
import com.example.commerce.business.category.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryAddRequestDto dto);
    void deleteCategory(Long categoryId);
    Category findById(Long categoryId);
    List<CategoryResponseDto> getCategoryList();
}
