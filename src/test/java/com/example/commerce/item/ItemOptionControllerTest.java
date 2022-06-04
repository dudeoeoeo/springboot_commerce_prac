package com.example.commerce.item;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.repository.ItemOptionRepository;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemOptionControllerTest extends RestDocsTestSupport {

    @Autowired
    private ItemOptionRepository optionRepository;

    private final String PREFIX = "/api/v1/option";
    private final String JSON_LOCATION = "/json/item/option";

    ItemOption addItemOption() {
        ItemOption itemOption = ItemOption.builder()
                .optionPrice(100000)
                .optionStock(10)
                .optionSize("L")
                .optionColor("white")
                .optionWeight(2.5)
                .build();

        return optionRepository.save(itemOption);
    }

    @Test
    @Transactional
    void updateItemOption() throws Exception {
        final String token = getAdminToken();
        final ItemOption itemOption = addItemOption();

        mockMvc.perform(
                put(PREFIX + "/{optionId}", itemOption.getId())
                    .header(JwtProperties.HEADER_STRING, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson(JSON_LOCATION+"/update.json"))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void deleteItemOption() throws Exception {
        final String token = getAdminToken();
        final ItemOption itemOption = addItemOption();

        mockMvc.perform(
                delete(PREFIX + "/{optionId}", itemOption.getId())
                    .header(JwtProperties.HEADER_STRING, token)
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
