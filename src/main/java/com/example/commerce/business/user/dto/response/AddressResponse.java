package com.example.commerce.business.user.dto.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressResponse {

     private Long id;

     private String zipcode;

     private String jiBun;

     private String road;

     private String building;

     private String detail;

}
