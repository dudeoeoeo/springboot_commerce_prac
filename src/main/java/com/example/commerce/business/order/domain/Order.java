package com.example.commerce.business.order.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
public class Order extends BaseTimeEntity {

    @Id @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Item> item;

    @OneToMany
    @JoinColumn(name = "item_option_id")
    private List<ItemOption> itemOption;

    public static Order createOrder(User user, List<Item> items, List<ItemOption> options) {
        return Order.builder()
                .user(user)
                .item(items)
                .itemOption(options)
                .build();
    }
}
