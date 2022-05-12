package com.example.commerce.user;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.user.domain.Address;
import com.example.commerce.business.user.dto.request.UserLoginRequest;
import com.example.commerce.business.user.dto.request.UserSignUpRequest;
import com.example.commerce.business.user.repository.AddressRepository;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.common.constant.JoinType;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends RestDocsTestSupport {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    final String email = "test@example.com";
    final String password = "12341234";

//    @Test
//    void 회원가입_테스트() throws Exception {
//        UserLoginRequest loginRequest = new UserLoginRequest();
//        loginRequest.setEmail(email);
//        loginRequest.setPassword(password);
//
//        String json = createJson(loginRequest);
//
//        mockMvc.perform(
//                post("/api/v1/auth/signUp")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    void 로그인_테스트() throws Exception {
        userSave();
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(null);

        String json = createJson(loginRequest);

        mockMvc.perform(
                post("/api/v1/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입_테스트() throws Exception {
        Map<String, String> signUpMap = new HashMap<>();
        signUpMap.put("name", "이름테스트");
        signUpMap.put("password", "12341234");
        signUpMap.put("email", "test@email.com");
        signUpMap.put("phone", "01012341234");
        signUpMap.put("joinType", String.valueOf(JoinType.COMMERCE));

        String contentJson = createJson(signUpMap);

        mockMvc.perform(
                post("/api/v1/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 주소추가_테스트() throws Exception {
        final String token = getToken();
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("zipcode", "07032");
        addressMap.put("jiBun", "서울특별시 동작구 상도1동 809");
        addressMap.put("road", "서울특별시 동작구 상도로38길 3-2");
        addressMap.put("building", "서울빌딩");
        addressMap.put("detail", "4층 402호");

        String json = createJson(addressMap);

        mockMvc.perform(
                post("/api/v1/user/address/add")
                    .header(JwtProperties.HEADER_STRING, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 주소리스트_가져오기() throws Exception {
        final String token = getToken();
        saveAddress(token);
        mockMvc.perform(
                get("/api/v1/user/address/list")
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 주소_업데이트_하기() throws Exception {
        final String token = getToken();
        saveAddress(token);
        final Optional<Address> address = addressRepository.findById(1L);
        Map<String, Object> map = new HashMap<>();
        map.put("zipcode", "11111");
        map.put("jiBun", "지번 변경");
        map.put("road", "도로명 변경");
        map.put("building", "빌딩 변경");
        map.put("detail", "상세 주소 변경");
        final String json = createJson(map);

        mockMvc.perform(
                post("/api/v1/user/address/{addressId}/update", 1L)
                .header(JwtProperties.HEADER_STRING, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
