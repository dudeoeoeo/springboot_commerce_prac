package com.example.commerce.item;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.repository.CategoryRepository;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.domain.ItemStatus;
import com.example.commerce.business.item.repository.ItemImageRepository;
import com.example.commerce.business.item.repository.ItemRepository;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest extends RestDocsTestSupport {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository imageRepository;

    private final String PREFIX = "/api/v1/item";

    public Item addItem() {
        final Category category = addCategory();
        final ItemImage itemImage = addItemImage();
        final Item item = Item.builder()
                .name("흰색 와이셔츠")
                .price(34900)
                .stock(10)
                .category(category)
                .itemStatus(ItemStatus.SELL)
                .itemImages(new ArrayList<>(Arrays.asList(itemImage)))
                .build();
        return itemRepository.save(item);
    }

    public void addItems() {
        final Category category = addCategory();
        for (int i = 1; i < 20; i++) {
            final ItemImage itemImage = addItemImage();
            final Item item = Item.builder()
                    .name("흰색 와이셔츠")
                    .price(34900)
                    .stock(10)
                    .category(category)
                    .itemStatus(ItemStatus.SELL)
                    .itemImages(new ArrayList<>(Arrays.asList(itemImage)))
                    .build();
            itemRepository.save(item);
        }
    }

    public ItemImage addItemImage() {
        final ItemImage itemImage = ItemImage.builder()
                .imageUrl("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                .viewYn(1)
                .orderedSeq(1)
                .build();
        return imageRepository.save(itemImage);
    }

    public Category addCategory() {
        final Category category = Category.builder()
                .classificationOne("패션의류")
                .classificationTwo("명품/수입의류")
                .classificationThree("남성 팬츠")
                .classificationDetail("데님 팬츠")
                .build();
        return categoryRepository.save(category);
    }

    @Test
    @Transactional
    void itemAdd() throws Exception {
        final String token = getAdminToken();
        addCategory();

        MockMultipartFile itemImage1 = new MockMultipartFile("images", "pants1.jpg", "image/jpg",
                readImage("/image/pants1.jpg").getBytes());
        MockMultipartFile itemImage2 = new MockMultipartFile("images", "pants2.jpg", "image/jpg",
                readImage("/image/pants2.jpg").getBytes());
        MockMultipartFile item_request = new MockMultipartFile("item_request", "/json/item/add.json", "application/json",
                readJson("/json/item/add.json").getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(
                multipart(PREFIX + "/add")
                        .file(itemImage1)
                        .file(itemImage2)
                        .file(item_request)
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void itemUpdate() throws Exception {
        final String token = getAdminToken();
        final Item item = addItem();

        mockMvc.perform(
                put(PREFIX + "/{itemId}", item.getId())
                    .header(JwtProperties.HEADER_STRING, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson("/json/item/update.json"))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void itemDelete() throws Exception {
        final String token = getAdminToken();
        final Item item = addItem();

        mockMvc.perform(
                delete(PREFIX + "/{itemId}", item.getId())
                        .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateItemImage() throws Exception {
        final String token = getAdminToken();
        final Item item = addItem();

        MockMultipartFile itemImage1 = new MockMultipartFile("image", "pants1.jpg", "image/jpg",
                readImage("/image/pants1.jpg").getBytes());

        mockMvc.perform(
                multipart(PREFIX + "/{imageId}/image", item.getItemImages().get(0).getId())
                        .file(itemImage1)
                        .header(JwtProperties.HEADER_STRING, token)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void deleteItemImage() throws Exception {
        final String token = getAdminToken();
        final Item item = addItem();

        mockMvc.perform(
                delete(PREFIX + "/{imageId}/image", item.getItemImages().get(0).getId())
                        .header(JwtProperties.HEADER_STRING, token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void getItemList() throws Exception {
        addItems();
        mockMvc.perform(
                get(PREFIX + "/list")
                    .param("searchPage", "1")
                    .param("searchCount", "10")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
