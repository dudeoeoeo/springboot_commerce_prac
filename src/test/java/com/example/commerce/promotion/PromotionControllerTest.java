package com.example.commerce.promotion;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.promotion.domain.PromotionLog;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

public class PromotionControllerTest extends RestDocsTestSupport {

    private final String PREFIX = "/api/v1/promotion";

    @Test
    @Transactional
    void addPromotion() throws Exception {
        final User user = userSave();
        final Item item = addItem();
        final String token = getTokenByUser(user);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("itemOptionId", item.getOptions().get(0).getId());
        requestMap.put("discountPercent", 5.0);
        requestMap.put("stock", 20);
        requestMap.put("useCoupon", true);
        requestMap.put("usePoint", false);
        requestMap.put("startDate", LocalDate.now());
        requestMap.put("endDate", LocalDate.now().plusWeeks(3));

        mockMvc.perform(
                post(PREFIX + "/add")
                .header(JwtProperties.HEADER_STRING, token)
                .content(createJson(requestMap))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getPromotionList() throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        addPromotions();

        mockMvc.perform(
                get(PREFIX + "/list")
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("searchPage", "1")
                        .param("searchCount", "10")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getPromotionLogs() throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        addPromotionLogs(user);

        mockMvc.perform(
                get(PREFIX + "/log")
                .header(JwtProperties.HEADER_STRING, token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("searchPage", "1")
                .param("searchCount", "10")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getPromotionLogDetail() throws Exception {
        final User user = userSave();
        final String token = getTokenByUser(user);
        final List<PromotionLog> promotionLogs = addPromotionLogs(user);

        mockMvc.perform(
                get(PREFIX + "/log/{promotionId}", promotionLogs.get(0).getPromotion().getId())
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
