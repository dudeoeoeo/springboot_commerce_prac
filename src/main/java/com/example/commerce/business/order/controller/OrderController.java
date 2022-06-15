package com.example.commerce.business.order.controller;

import com.example.commerce.business.order.domain.OrderStatus;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.order.service.OrderService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController extends CommonUtil {

    private final OrderService orderService;

    @PostMapping("/add")
    public SuccessResponse newOrder(HttpServletRequest request,
                                    @Valid @RequestBody OrderRequest dto,
                                    BindingResult bindingResult)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), orderService.newOrder(userId, dto));
    }

    @PatchMapping("/status/{orderId}")
    public SuccessResponse updateOrder(@PathVariable Long orderId,
                                       @RequestParam("orderStatus") OrderStatus orderStatus)
    {
        return SuccessResponse.of(HttpStatus.OK.value(), orderService.updateOrder(orderId, orderStatus));
    }
}
