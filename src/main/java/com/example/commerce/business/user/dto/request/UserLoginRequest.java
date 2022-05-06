package com.example.commerce.business.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
