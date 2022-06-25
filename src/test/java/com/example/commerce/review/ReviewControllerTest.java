package com.example.commerce.review;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends RestDocsTestSupport {

    private final String PREFIX = "/api/v1/review";

    @Test
    @Transactional
    void addReview() throws Exception {
        final User user = userSave();
        final Cart cart = saveCart(user);
        final String token = getTokenByUser(user);
        final Item item = addItem();
        final OrderOption orderOption = addOrderOption(user);

        MockMultipartFile reviewImage1 = new MockMultipartFile("review_image", "pants1.jpg", "image/jpg",
                readImage("/image/pants1.jpg").getBytes());
        MockMultipartFile reviewImage2 = new MockMultipartFile("review_image", "pants2.jpg", "image/jpg",
                readImage("/image/pants2.jpg").getBytes());
        MockMultipartFile review_request = new MockMultipartFile("review_request", "/json/review/add.json", "application/json",
                readJson("/json/review/add.json").getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
                multipart(PREFIX + "/add")
                        .file(reviewImage1)
                        .file(reviewImage2)
                        .file(review_request)
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
