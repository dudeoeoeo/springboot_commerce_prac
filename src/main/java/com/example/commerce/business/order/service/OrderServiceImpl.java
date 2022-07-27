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
import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.service.PointService;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.domain.PromotionLog;
import com.example.commerce.business.promotion.repository.PromotionLogRepository;
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
    private final PromotionLogRepository promotionLogRepository;

    @Transactional
    public ResultResponse newOrder(Long userId, OrderRequest dto) {
        final User user = userService.findUserByUserId(userId);
        List<OrderOption> orderOptions = new ArrayList<>();

        Coupon coupon = null;
        if (dto.getCouponId() > 0) {
            coupon = couponService.findById(dto.getCouponId());
            if (dto.getTotalPrice() < coupon.getCondition())
                throw new IllegalArgumentException("쿠폰을 사용할 수 없는 주문입니다.");
            else if (coupon.isCouponUse())
                throw new IllegalArgumentException("이미 사용한 쿠폰입니다.");
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

        return ResultResponse.success("주문이 완료되었습니다.");
    }

    /**
     * TODO: promotion 정책, (쿠폰, 포인트 사용 여부), log 관리
     */
    @Override
    @Transactional
    public ResultResponse buyPromotion(Long userId, OrderPromotionRequest dto) {
        final User user = userService.findUserByUserId(userId);
        final Coupon coupon = couponService.findById(dto.getCouponId());
        final Point point = pointService.findByUser(user);
        List<OrderOption> orderOptions = new ArrayList<>();

        final List<PromotionSaveForm> promotions = dto.getPromotionForms().stream()
                .map(e -> PromotionSaveForm.newPromotionSaveForm(promotionService.findById(e.getPromotionId()), e))
                .collect(Collectors.toList());
        int totalCouponPrice = 0, totalPointPrice = 0;

        for (PromotionSaveForm p : promotions) {
            if (p.getPromotion().isUseCoupon())
                totalCouponPrice += p.getOrderPrice();

            if (p.getPromotion().isUsePoint())
                totalPointPrice += p.getOrderPrice();
        }

        couponService.useCoupon(userId, dto.getCouponId(), totalCouponPrice);

        if (point.getPoint() < dto.getPoint())
            throw new IllegalThreadStateException("보유하신 포인트보다 많은 금액을 사용하실 수 없습니다.");
        int usePoint = 0;
        if (totalPointPrice > dto.getPoint()) {
            pointService.usePoint(user, dto.getPoint());
            usePoint = dto.getPoint();
        }
        else {
            pointService.usePoint(user, totalPointPrice);
            usePoint = totalPointPrice;
        }

        for (PromotionSaveForm form : promotions) {
            promotionLogRepository.save(
                    PromotionLog.newPromotionLog(
                            form, user, coupon, usePoint
                    )
            );
        }
        return ResultResponse.success("상품을 구매했습니다.");
    }

    public Page<OrderListDto> getOrderList(Long userId, int page, int size) {
        final User user = userService.findUserByUserId(userId);
        Pageable pageable = PageRequest.of(getSearchPage(page), size);
        return orderRepository.getOrderList(pageable, user);
    }

    public Orders findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalThreadStateException("해당 주문내역을 찾을 수 없습니다."));
    }

    public OrderOptionDetailResponse getOrderOptionDetail(Long orderOptionId) {
        final OrderOption option = orderOptionService.findById(orderOptionId);
        return OrderOptionDetailResponse.createOptionDetail(option);
    }

    public int getSearchPage(int page) {
        return page - 1 < 0 ? page : page - 1;
    }

}
