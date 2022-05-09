package com.example.commerce.business.user.domain;

import com.example.commerce.business.user.dto.request.UserSignUpRequest;
import com.example.commerce.common.constant.BaseTimeEntity;
import com.example.commerce.common.constant.JoinType;
import com.example.commerce.common.constant.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String phone;

    private Role role;

    private JoinType joinType;

    public static User newUser(UserSignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .role(Role.USER)
                .joinType(JoinType.valueOf(signUpRequest.getJoinType()))
                .build();
    }
}
