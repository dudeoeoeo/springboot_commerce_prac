package com.example.commerce.business.review.domain;

import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_image")
public class ReviewImage extends BaseTimeEntity {

    @Id @Column(name = "review_image_id")
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

    public static ReviewImage newReviewImage(String imageUrl, int orderedSeq) {
        return ReviewImage.builder()
                .imageUrl(imageUrl)
                .viewYn(1)
                .orderedSeq(orderedSeq)
                .build();
    }
}
