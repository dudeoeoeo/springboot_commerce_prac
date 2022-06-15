package com.example.commerce.order;

import com.example.commerce.business.auth.util.JwtProperties;
import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.domain.PaymentStatus;
import com.example.commerce.business.order.dto.request.OrderForm;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.util.*;

public class OrdersControllerTest extends RestDocsTestSupport {

    private final String PREFIX = "/api/v1/orders";

    @Test
    @Transactional
    void newOrder() throws Exception {
        final User user = userSave();
        final Cart cart = saveCart(user);
        final String token = getTokenByUser(user);
        final Item item = addItem();

        Map<String, Object> orderFormMap = new HashMap<>();
        orderFormMap.put("itemId", item.getId());
        orderFormMap.put("itemOptionId", item.getOptions().get(0).getId());
        orderFormMap.put("stock", 2);
        orderFormMap.put("price", item.getOptions().get(0).getOptionPrice() * 2);

        Map<String, Object> request = new HashMap<>();
        request.put("orderForms", new ArrayList<>(Arrays.asList(orderFormMap)));
        request.put("totalPrice", item.getOptions().get(0).getOptionPrice() * 2);
        request.put("deliveryFee", 2500);
        request.put("paymentStatus", PaymentStatus.CARD);

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
    void 여러상품주문() throws Exception {
        final User user = userSave();
        final Cart cart = saveCart(user);
        final String token = getTokenByUser(user);
        final List<Item> items = addItems();

        List<Map<String, Object>> orderForms = new ArrayList<>();

        int stock = 1;
        int totalPrice = 0;

        for (Item item : items) {
            Map<String, Object> orderFormMap = new HashMap<>();
            orderFormMap.put("itemId", item.getId());
            orderFormMap.put("itemOptionId", item.getOptions().get(0).getId());
            orderFormMap.put("stock", stock);
            orderFormMap.put("price", item.getOptions().get(0).getOptionPrice() * stock);
            totalPrice += item.getOptions().get(0).getOptionPrice() * stock++;
            orderForms.add(orderFormMap);
        }

        Map<String, Object> request = new HashMap<>();
        request.put("orderForms", orderForms);
        request.put("totalPrice", totalPrice);
        request.put("deliveryFee", totalPrice > 50000 ? 0 : 2500);
        request.put("paymentStatus", PaymentStatus.CARD);

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
    void updateOrder() throws Exception {
        final Orders orders = addOrder();

        mockMvc.perform(
                patch(PREFIX + "/status/{orderId}", orders.getId())
                    .param("orderStatus", String.valueOf(OrderStatus.CANCEL))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
