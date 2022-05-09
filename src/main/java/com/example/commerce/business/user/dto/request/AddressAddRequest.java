package com.example.commerce.business.user.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressAddRequest {
    private String zipcode;

    private String jiBun;

    private String road;

    private String building;

    private String detail;
}
