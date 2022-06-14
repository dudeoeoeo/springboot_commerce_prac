package com.example.commerce.business.order.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
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

    public static Orders createOrder(User user, List<Item> items, List<ItemOption> options) {
        return Orders.builder()
                .user(user)
                .item(items)
                .itemOption(options)
                .build();
    }
}
