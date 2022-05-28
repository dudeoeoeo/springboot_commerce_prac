package com.example.commerce.business.item.domain;

import com.example.commerce.business.item.dto.request.ItemOptionAddRequestDto;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item_option")
public class ItemOption extends BaseTimeEntity {

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
    private int optionWeight;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static ItemOption newItemOption(ItemOptionAddRequestDto dto) {
        return ItemOption.builder()
                .optionPrice(dto.getOptionPrice())
                .optionStock(dto.getOptionStock())
                .optionSize(dto.getOptionSize())
                .optionColor(dto.getOptionColor())
                .optionWeight(dto.getOptionWeight())
                .build();
    }

    public void updateItemOption(ItemOptionAddRequestDto dto) {
        this.optionPrice = dto.getOptionPrice();
        this.optionStock = dto.getOptionStock();
        this.optionSize = dto.getOptionSize();
        this.optionColor = dto.getOptionColor();
        this.optionWeight = dto.getOptionWeight();
    }

    public void deleteItemOption(User user) {
        this.deleteBy = user.getName();
        this.deleteYn = 1;
        this.deleteDt = LocalDateTime.now().withNano(0);
    }
}
