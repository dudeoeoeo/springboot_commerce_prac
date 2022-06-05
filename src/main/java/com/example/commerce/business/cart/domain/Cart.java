package com.example.commerce.business.cart.domain;

import com.example.commerce.business.cart.dto.request.AddCartItem;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart extends BaseTimeEntity {

    @Id @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private List<ItemOption> options = new ArrayList<>();

    public static Cart createCart(User user) {
        return Cart.builder()
                .user(user)
                .build();
    }

    /**
     * TODO: 이미 추가한 상품이면 중복체크, 상품 옵션 수량 체크 필요
     */
    public void addItem(Item item, ItemOption option) {
        this.items.add(item);
        this.options.add(option);
    }
}
