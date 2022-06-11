package com.example.commerce.cart;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

public class CartControllerTest extends RestDocsTestSupport {


    private final String PREFIX = "/api/v1/cart";

    @Test
    @Transactional
    void newCart() throws Exception {
        final String token = getToken();
        final Item item = addItem();

        Map<String, Object> request = new HashMap<>();
        request.put("itemId", item.getId());
        request.put("itemOptionId", item.getOptions().get(0).getId());
        request.put("optionStock", 2);

        mockMvc.perform(
                post(PREFIX + "/add")
                    .content(createJson(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 상품_최대수량_초과() throws Exception {
        final String token = getToken();
        final Item item = addItem();

        Map<String, Object> request = new HashMap<>();
        request.put("itemId", item.getId());
        request.put("itemOptionId", item.getOptions().get(0).getId());
        request.put("optionStock", 12);

        mockMvc.perform(
                post(PREFIX + "/add")
                        .content(createJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 상품_최소개수() throws Exception {
        final String token = getToken();
        final Item item = addItem();

        Map<String, Object> request = new HashMap<>();
        request.put("itemId", item.getId());
        request.put("itemOptionId", item.getOptions().get(0).getId());
        request.put("optionStock", -10);

        mockMvc.perform(
                post(PREFIX + "/add")
                        .content(createJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getCartItem() throws Exception {
        final User user = userSave();
        final Cart cart = saveCart(user);
        final String token = getTokenByUser(user);
        final Item item = addItem();
        saveCartItem(item, cart);

        mockMvc.perform(
            get(PREFIX + "/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProperties.HEADER_STRING, token)
                .param("searchPage", "1")
                .param("searchCount", "10")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
