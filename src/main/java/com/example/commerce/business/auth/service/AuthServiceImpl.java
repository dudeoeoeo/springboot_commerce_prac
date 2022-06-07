package com.example.commerce.business.auth.service;

import com.example.commerce.business.auth.dto.response.TokenResponse;
import com.example.commerce.business.auth.repository.TokenRepository;
import com.example.commerce.business.auth.util.TokenProvider;
import com.example.commerce.business.cart.service.CartService;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.business.user.dto.request.UserSignUpRequest;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.common.config.security.auth.UserDetail;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final CartService cartService;

    @Transactional
    public ResultResponse signUp(UserSignUpRequest signUpRequest) {
        User newUser = User.newUser(signUpRequest, passwordEncoder);
        final User savedUser = userRepository.save(newUser);
        cartService.newCart(savedUser);
        return ResultResponse.success("회원가입 되었습니다.");
    }

    @Transactional
    public TokenResponse signIn(UserLoginRequest loginRequest) {
        final Optional<User> findUser = userRepository.findByEmail(loginRequest.getEmail());
        if (!findUser.isPresent()) {
            logger.warn("email 오류: {}", loginRequest.getEmail());
            throw new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요.");
        }
        else if (passwordEncoder.matches(loginRequest.getPassword(), findUser.get().getPassword())) {
            logger.warn("password 오류, email: {}", loginRequest.getEmail());
        }
        UserDetail userDetail = new UserDetail(findUser.get());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetail, loginRequest.getPassword(), userDetail.getAuthorities());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(tokenProvider.generateToken(findUser.get().getId()));
        tokenResponse.setRefreshToken(tokenProvider.refreshToken(findUser.get().getId()));

        return tokenResponse;
    }
}
