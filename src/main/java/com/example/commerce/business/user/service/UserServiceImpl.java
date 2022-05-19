package com.example.commerce.business.user.service;

import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User foundUserByUserId(Long userId) {
         return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.")
        );
    }
}
