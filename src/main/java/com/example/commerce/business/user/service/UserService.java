package com.example.commerce.business.user.service;

import com.example.commerce.business.user.domain.User;

public interface UserService {

    User findUserByUserId(Long userId);
}
