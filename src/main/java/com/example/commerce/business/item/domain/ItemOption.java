package com.example.commerce.business.item.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item_option")
public class ItemOption {

    @Id @Column(name = "item_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_price")
    private int optionPrice;

    @Column(name = "option_stock")
    private int optionStock;

    @Column(name = "option_size")
    private int optionSize;

    @Column(name = "option_color")
    private int optionColor;

    @Column(name = "option_weight")
    private int option_weight;

}
