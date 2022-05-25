package com.example.commerce.business.category.service;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.dto.request.CategoryAddRequestDto;
import com.example.commerce.business.category.dto.response.CategoryResponseDto;
import com.example.commerce.business.category.repository.CategoryRepository;
import com.example.commerce.business.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void addCategory(CategoryAddRequestDto dto) {
        final Category category = Category.newCategory(dto);

        categoryRepository.save(category);

    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 category를 찾을 수 없습니다."));
        // dirty checking
        category.deleteCategory();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoryList() {
        final List<Category> categoryList = categoryRepository.findAllByDeleteYnOrderByIdDesc(0);
        if (categoryList.isEmpty())
            return null;
        return categoryList.stream()
                .map(e -> objectMapper.convertValue(e, CategoryResponseDto.class))
                .collect(Collectors.toList());
    }
}
