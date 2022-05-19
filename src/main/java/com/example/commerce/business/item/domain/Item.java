package com.example.commerce.business.item.domain;

import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item extends BaseTimeEntity {

    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TODO: Unique itemId 를 사용해서 어떤 작업에서 효율적으로 할 지..
     */
//    @Column(name = "item_id", unique = true)
//    private String itemId;

    private String name;

    private int price;

    private int stock;

    private int weight;

    @OneToMany
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany
    private List<ItemOption> options = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    private ItemStatus itemStatus;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;
    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static Item newItem(User user, ItemAddRequestDto dto, List<ItemImage> itemImages) {
        return Item.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .weight(dto.getWeight())
                .itemImages(itemImages)
                .itemStatus(ItemStatus.SELL)
                .build();
    }
}
