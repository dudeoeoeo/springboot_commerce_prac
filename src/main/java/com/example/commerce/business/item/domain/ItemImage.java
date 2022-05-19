package com.example.commerce.business.item.domain;

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
@Table(name = "item_image")
public class ItemImage extends BaseTimeEntity {

    @Id @Column(name = "item_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "view_yn")
    private int viewYn;

    @Column(name = "ordered_seq")
    private int orderedSeq;

    @Column(name = "delete_yn")
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static ItemImage newItemImage(User user, String imageUrl, int orderedSeq) {
        return ItemImage.builder()
                .imageUrl(imageUrl)
                .viewYn(1)
                .orderedSeq(orderedSeq)
                .build();
    }
}
