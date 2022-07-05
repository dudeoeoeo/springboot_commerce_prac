package com.example.commerce.business.review.domain;

import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.order.domain.OrderOption;
import com.example.commerce.business.review.dto.request.AddReviewRequest;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseTimeEntity {

    @Id @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "order_option_id")
    private OrderOption orderOption;

    @OneToMany
    @JoinColumn(name = "review_image_id")
    private List<ReviewImage> reviewImages;

    @Column(name = "content")
    private String content;

    @Column(name = "star")
    private double star;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static Review createReview(User user, Item item,
                                      OrderOption orderOption,
                                      AddReviewRequest dto,
                                      List<ReviewImage> reviewImages)
    {
        return Review.builder()
                .user(user)
                .item(item)
                .orderOption(orderOption)
                .content(dto.getContent())
                .star(dto.getStar())
                .reviewImages(reviewImages)
                .build();
    }
    public void deleteReview(User user) {
        this.deleteYn = 1;
        this.deleteBy = user.getName();
        this.deleteDt = LocalDateTime.now().withNano(0);
    }
}
