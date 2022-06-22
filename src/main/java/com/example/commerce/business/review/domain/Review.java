package com.example.commerce.business.review.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseTimeEntity {

    @Id @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "order_option_id")
    private OrderOption orderOption;

    @Column(name = "content")
    private String content;

    @Column(name = "star")
    private double star;
}
