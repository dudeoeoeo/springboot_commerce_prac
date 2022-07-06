package com.example.commerce.business.point.domain;

import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// Point Log

@Entity @Getter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "point_option_id")
public class PointOption extends BaseTimeEntity {

    @Id @Column(name = "point_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @Column(name = "money")
    private int money;

    @Column(name = "use")
    private PointUse pointUse;

    @Column(name = "type")
    private PointType pointType;

    @Column(name = "delete_yn", insertable = false)
    private int deleteYn;

    @Column(name = "delete_by", insertable = false)
    private String deleteBy;

    @Column(name = "delete_dt", insertable = false)
    private LocalDateTime deleteDt;

    public static PointOption newPointOption(Point point, int money, PointType type, PointUse use) {
        return PointOption.builder()
                .point(point)
                .money(money)
                .pointType(type)
                .pointUse(use)
                .build();
    }
}
