package com.example.commerce.business.auth.domain;

import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.constant.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseTimeEntity {

    @Id @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
