package com.example.commerce.business.user.domain;

import com.example.commerce.common.constant.BaseTimeEntity;
import com.example.commerce.common.constant.JoinType;
import com.example.commerce.common.constant.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private Role role;

    private JoinType joinType;
}
