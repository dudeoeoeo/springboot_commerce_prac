package com.example.commerce.user;

import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.net.URISyntaxException;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends RestDocsTestSupport {

    @Autowired
    private UserRepository userRepository;

    final String email = "test@example.com";
    final String password = "12341234";

//    @LocalServerPort
//    private int port;
//
//    private RestTemplate restTemplate = new RestTemplate();
//
//    private URI uri(String path) throws URISyntaxException {
//        return new URI(format("http://localhost:%d%s", port, path));
//    }

    @Test
    void 회원가입_테스트() throws Exception {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String json = createJson(loginRequest);

        mockMvc.perform(
                post("/api/v1/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인_테스트() throws Exception {
        userSave();
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String json = createJson(loginRequest);

        mockMvc.perform(MockMvcRequestBuilders
        .post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @DisplayName("1. JWT로 로그인을 시도한다.")
//    @Test
//    void JWT_로그인_시도() throws URISyntaxException {
//        userSave();
//        UserLoginRequest loginRequest = new UserLoginRequest();
//        loginRequest.setEmail(email);
//        loginRequest.setPassword(password);
//
//        HttpEntity<UserLoginRequest> body = new HttpEntity<>(loginRequest);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(uri("/shop/login"), HttpMethod.POST, body, String.class);
//
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        System.out.println(responseEntity.getHeaders().get("Authentication"));
//    }
}
