package com.example.commerce.business.item.domain;

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

    @Column(name = "item_id", unique = true)
    private String itemId;

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
}
