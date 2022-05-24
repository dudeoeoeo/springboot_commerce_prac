package com.example.commerce.business.category.service;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.dto.request.CategoryAddRequestDto;
import com.example.commerce.business.category.repository.CategoryRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserService userService;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void addCategory(CategoryAddRequestDto dto) {
        final Category category = Category.newCategory(dto);

        categoryRepository.save(category);

    }
}
