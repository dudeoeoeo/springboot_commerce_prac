package com.example.commerce.business.category.service;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.dto.request.CategoryAddRequestDto;
import com.example.commerce.business.category.dto.response.CategoryResponseDto;
import com.example.commerce.business.category.repository.CategoryRepository;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
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
        final Optional<Item> item = itemService.findByCategoryId(categoryId);
        if (item.isPresent())
            throw new IllegalArgumentException("판매중인 상품의 카테고리 입니다.");
        // dirty checking
        category.deleteCategory();
    }

    @Transactional(readOnly = true)
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 카테고리를 찾을 수 없습니다."));
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
