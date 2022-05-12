package com.example.commerce.business.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateAddressRequest {

    private String zipcode;

    private String jiBun;

    private String road;

    private String building;

    private String detail;
}
