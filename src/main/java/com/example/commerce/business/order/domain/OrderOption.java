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

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "finished_date")
    private LocalDateTime finishedDate;

    public static OrderOption createOrderOption(OrderForm dto) {
        return OrderOption.builder()
                .price(dto.getPrice())
                .stock(dto.getStock())
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .build();
    }
    public void addOrder(Orders orders) {
        this.orders = orders;
    }
}
