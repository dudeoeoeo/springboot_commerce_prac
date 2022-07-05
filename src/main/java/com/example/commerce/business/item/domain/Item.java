package com.example.commerce.business.item.domain;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.item.dto.request.ItemAddRequestDto;
import com.example.commerce.business.item.dto.request.ItemUpdateRequestDto;
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

    @Id @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TODO: Unique itemId 를 사용해서 어떤 작업에서 효율적으로 할 지..
     */
//    @Column(name = "item_id", unique = true)
//    private String itemId;

    private String name;

    /**
     * TODO: item option 에서 가격과 수량 등을 정하고
     *       item 자체는 category 분류와 상품 이름만 결정
     */
//    private int price;
//    private int stock;
//    private int weight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany
    private List<ItemOption> options = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    private ItemStatus itemStatus;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static Item newItem(User user, ItemAddRequestDto dto,
                               List<ItemImage> itemImages, Category category,
                               List<ItemOption> options) {
        return Item.builder()
                .name(dto.getName())
//                .price(dto.getPrice())
//                .stock(dto.getStock())
//                .weight(dto.getWeight())
                .category(category)
                .itemImages(itemImages)
                .options(options)
                .itemStatus(ItemStatus.SELL)
                .build();
    }

    public void updateItemContent(ItemUpdateRequestDto dto) {
        this.name = dto.getName();
        this.itemStatus = dto.getItemStatus();
    }

    public void deleteItem(User user) {
        this.deleteYn = 1;
        this.deleteBy = user.getName();
        this.deleteDt = LocalDateTime.now().withNano(0);
    }
}
