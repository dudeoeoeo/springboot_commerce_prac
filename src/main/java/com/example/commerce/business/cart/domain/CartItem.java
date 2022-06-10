package com.example.commerce.business.cart.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem extends BaseTimeEntity {
    /**
     * Cart 에 추가하는 상품은 cartItem 테이블에 저장
     * cart fk, item fk, option fk 저장
     *
     * CartItem table 의 데이터는 쉽게 저장되고 지워질 수 있으므로
     * data를 보존하는 것보다 Log table 을 만들어 기록하는게 더 낫다 ..
     */

    @Id @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    private int stock;

    public static CartItem createCartItem(Cart cart, Item item,
                                          ItemOption option, int stock) {
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .itemOption(option)
                .stock(stock)
                .build();
    }

    public void addStock(int stock) {
        this.stock += stock;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }
}
