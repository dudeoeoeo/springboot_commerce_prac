package com.example.commerce.business.order.domain;

import com.example.commerce.business.order.dto.request.OrderForm;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_option")
public class OrderOption extends BaseTimeEntity {

    @Id @Column(name = "order_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "delivery_fee")
    private int deliveryFee;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "finished_date")
    private LocalDateTime finishedDate;

    public static OrderOption createOrderOption(OrderForm dto, int deliveryFee, PaymentStatus paymentStatus) {
        return OrderOption.builder()
                .price(dto.getPrice())
                .stock(dto.getStock())
                .deliveryFee(deliveryFee)
                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(paymentStatus)
                .build();
    }
    public void addOrder(Order order) {
        this.order = order;
    }
}
