package com.example.commerce.business.order.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "finished_date")
    private LocalDateTime finishedDate;

    public static OrderOption createOrderOption(OrderForm dto, Item item, ItemOption option) {
        return OrderOption.builder()
                .price(dto.getPrice())
                .stock(dto.getStock())
                .item(item)
                .itemOption(option)
                .orderStatus(OrderStatus.ORDER)
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .build();
    }
    public void addOrder(Orders orders) {
        this.orders = orders;
    }

    public void updateTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
