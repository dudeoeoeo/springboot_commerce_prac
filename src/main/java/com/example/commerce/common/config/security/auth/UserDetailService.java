package com.example.commerce.common.config.security.auth;

import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 없습니다. 로그인 이메일: " + email)
        );
        return new UserDetail(user);
    }
}
