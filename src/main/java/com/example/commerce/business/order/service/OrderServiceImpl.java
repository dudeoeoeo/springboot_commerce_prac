package com.example.commerce.business.order.service;

import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.service.CouponService;
import com.example.commerce.business.item.service.ItemOptionService;
import com.example.commerce.business.item.service.ItemService;
import com.example.commerce.business.order.domain.Orders;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.order.dto.request.OrderPromotionRequest;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.order.dto.request.PromotionSaveForm;
import com.example.commerce.business.order.dto.response.OrderListDto;
import com.example.commerce.business.order.dto.response.OrderOptionDetailResponse;
import com.example.commerce.business.order.repository.OrderRepository;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.service.PointService;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.service.PromotionService;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.service.UserService;
import com.example.commerce.common.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemOptionService optionService;
    private final OrderOptionService orderOptionService;
    private final PointService pointService;
    private final CouponService couponService;
    private final PromotionService promotionService;

    @Transactional
    public ResultResponse newOrder(Long userId, OrderRequest dto) {
        final User user = userService.findUserByUserId(userId);
        List<OrderOption> orderOptions = new ArrayList<>();

        Coupon coupon = null;
        if (dto.getCouponId() > 0) {
            coupon = couponService.findById(dto.getCouponId());
            if (dto.getTotalPrice() < coupon.getCondition())
                throw new IllegalArgumentException("????????? ????????? ??? ?????? ???????????????.");
            else if (coupon.isCouponUse())
                throw new IllegalArgumentException("?????? ????????? ???????????????.");
        }
        if (dto.getUsePoint() > 0) {
            pointService.usePoint(user, dto.getUsePoint());
        }

        dto.getOrderForms().forEach(orderForm -> {
//            items.add(itemService.findByItemId(orderForm.getItemId()));
//            options.add(optionService.findById(orderForm.getItemOptionId()));
            orderOptions.add(OrderOption.createOrderOption(
                    orderForm,
                    itemService.findByItemId(orderForm.getItemId()),
                    optionService.findById(orderForm.getItemOptionId())));
        });

        final Orders saveOrder = orderRepository.save(Orders.createOrder(user, dto, coupon));
        orderOptions.forEach(orderOption -> orderOption.addOrder(saveOrder));
        orderOptionService.newOrderOption(orderOptions);
        pointService.plusPoint(user, dto.getTotalPrice(), PointType.ORDER);

        return ResultResponse.success("????????? ?????????????????????.");
    }

    /**
     * TODO: promotion ??????, (??????, ????????? ?????? ??????), log ??????
     */
    @Override
    public ResultResponse buyPromotion(Long userId, OrderPromotionRequest dto) {
        final User user = userService.findUserByUserId(userId);
        List<OrderOption> orderOptions = new ArrayList<>();
        final List<PromotionSaveForm> promotions = dto.getPromotionForms().stream()
                .map(e -> PromotionSaveForm.newPromotionSaveForm(promotionService.findById(e.getPromotionId()), e))
                .collect(Collectors.toList());

        return null;
    }

    public Page<OrderListDto> getOrderList(Long userId, int page, int size) {
        final User user = userService.findUserByUserId(userId);
        Pageable pageable = PageRequest.of(getSearchPage(page), size);
        return orderRepository.getOrderList(pageable, user);
    }

    public Orders findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalThreadStateException("?????? ??????????????? ?????? ??? ????????????."));
    }

    public OrderOptionDetailResponse getOrderOptionDetail(Long orderOptionId) {
        final OrderOption option = orderOptionService.findById(orderOptionId);
        return OrderOptionDetailResponse.createOptionDetail(option);
    }

    public int getSearchPage(int page) {
        return page - 1 < 0 ? page : page - 1;
    }

}
