package com.example.commerce.business.category.domain;

import com.example.commerce.business.category.dto.request.CategoryAddRequestDto;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category extends BaseTimeEntity {

    @Id @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 현재는 대분류 중분류 소분류 상세분류를 한 개의 테이블에서 관리
     */
    @Column(name = "classification_one")
    private String classificationOne;

    @Column(name = "classification_two")
    private String classificationTwo;

    @Column(name = "classification_three")
    private String classificationThree;

    @Column(name = "classification_detail")
    private String classificationDetail;

    @Column(name = "delete_yn")
    private int deleteYn;

    public static Category newCategory(CategoryAddRequestDto dto) {
        return Category.builder()
                .classificationOne(dto.getClassificationOne())
                .classificationTwo(dto.getClassificationTwo())
                .classificationThree(dto.getClassificationThree())
                .classificationDetail(dto.getClassificationDetail())
                .deleteYn(0)
                .build();
    }
}
