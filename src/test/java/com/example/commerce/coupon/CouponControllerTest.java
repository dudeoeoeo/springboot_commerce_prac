package com.example.commerce.coupon;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CouponControllerTest extends RestDocsTestSupport {

    private final static String PREFIX = "/api/v1/coupon";

    @Test
    @Transactional
    void addCoupon () throws Exception {
        final User admin = adminSave();
        final String token = getTokenByUser(admin);

        final User user = userSave();

        Map<String, Object> request = new HashMap<>();
        request.put("userId", user.getId());
        request.put("condition", 5000);
        request.put("discount", 3000);
        request.put("expiredTime", 6);

        mockMvc.perform(
                post(PREFIX + "/add")
                        .header(JwtProperties.HEADER_STRING, token)
                        .content(createJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    void getCouponList () throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        addCoupons(user);

        mockMvc.perform(
                get(PREFIX + "/list")
                .header(JwtProperties.HEADER_STRING, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    void getCouponDetail () throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        final List<Coupon> coupons = addCoupons(user);

        mockMvc.perform(
                get(PREFIX + "/{couponId}", coupons.get(1).getId())
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    void useCoupon () throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        final List<Coupon> coupons = addCoupons(user);

        mockMvc.perform(
                get(PREFIX + "/use/{couponId}", coupons.get(0).getId())
                        .header(JwtProperties.HEADER_STRING, token)
                        .param("price", "33000")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
