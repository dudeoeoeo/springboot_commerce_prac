package com.example.commerce.business.cart.domain;

import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_log")
public class CartLog extends BaseTimeEntity {
    /**
     * 장바구니에서 삭제 시 Log table에 저장
     */

    @Id @Column(name = "cart_rog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static CartLog deleteCartItem(User user, ItemOption option) {
        return CartLog.builder()
                .user(user)
                .itemOption(option)
                .deleteYn(1)
                .deleteDt(LocalDateTime.now().withNano(0))
                .build();
    }
}
