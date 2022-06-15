package com.example.commerce.business.order.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.order.dto.request.OrderRequest;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders extends BaseTimeEntity {

    @Id @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> item;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemOption> itemOption;

    @Column(name = "delivery_fee")
    private int deliveryFee;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    public static Orders createOrder(User user,
                                     List<Item> items,
                                     List<ItemOption> options,
                                     OrderRequest dto) {
        return Orders.builder()
                .deliveryFee(dto.getDeliveryFee())
                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(dto.getPaymentStatus())
                .user(user)
                .item(items)
                .itemOption(options)
                .build();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}