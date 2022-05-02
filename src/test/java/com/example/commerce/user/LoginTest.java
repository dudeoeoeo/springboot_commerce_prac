package com.example.commerce.user;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.common.constant.JoinType;
import com.example.commerce.common.constant.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    ObjectMapper objectMapper = new ObjectMapper();

    private static String KEY_EMAIL = "email";
    private static String KEY_PASSWORD = "password";
    private static String USERNAME = "username";
    private static String PASSWORD = "123456789";
    private static String EMAIL = "test@example.com";

    private static String LOGIN_RUL = "/login";

    private void clear(){
        em.flush();
        em.clear();
    }

    @BeforeEach
    private void init(){
        userRepository.save(User.builder()
                .password(bCryptPasswordEncoder.encode(PASSWORD))
                .name("Member1")
                .email(EMAIL)
                .role(Role.USER)
                .build());
        clear();
    }

    private Map getUsernamePasswordMap(String username, String password){
        Map<String, String> map = new HashMap<>();
        map.put(KEY_EMAIL, username);
        map.put(KEY_PASSWORD, password);
        return map;
    }

    private ResultActions perform(String url, MediaType mediaType, Map usernamePasswordMap) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .contentType(mediaType)
                .content(objectMapper.writeValueAsString(usernamePasswordMap)));
    }

    @Test
    public void 로그인_성공() throws Exception {

        //given
        Map<String, String> map = getUsernamePasswordMap(EMAIL, PASSWORD);

        //when
        MvcResult result = perform(LOGIN_RUL, APPLICATION_JSON, map)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThat(result.getResponse().getHeader(JwtProperties.HEADER_STRING)).isNotNull();
        assertThat(result.getResponse().getHeader(JwtProperties.REFRESH_TOKEN_HEADER)).isNotNull();
    }
}
