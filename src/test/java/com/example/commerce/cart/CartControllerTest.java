package com.example.commerce.cart;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.item.domain.Item;
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
        request.put("optionPrice", 12000);
        request.put("optionStock", 2);
        request.put("optionSize", "XL");
        request.put("optionColor", "red");
        request.put("optionWeight", 1.5);

        mockMvc.perform(
                post(PREFIX + "/add")
                    .content(createJson(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
