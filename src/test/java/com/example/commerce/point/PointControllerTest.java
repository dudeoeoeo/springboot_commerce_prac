package com.example.commerce.point;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;


public class PointControllerTest extends RestDocsTestSupport {

    private final String PREFIX = "/api/v1/point";

    @Test
    @Transactional
    void getPoints() throws Exception {
        final User user = userSave();
        addPoints(user);
        final String token = getTokenByUser(user);

        mockMvc.perform(
                get(PREFIX)
                .header(JwtProperties.HEADER_STRING, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getPointLog() throws Exception {
        final User user = userSave();
        addPoints(user);
        final String token = getTokenByUser(user);

        mockMvc.perform(
                get(PREFIX + "/log")
                        .header(JwtProperties.HEADER_STRING, token)
                        .param("searchPage", "1")
                        .param("searchCount", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
