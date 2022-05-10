package com.example.commerce.business.user.dto.response;

import com.example.commerce.business.user.domain.Address;
import lombok.Getter;

@Getter
public class AddressResponse {

     private Long id;

     private String zipcode;

     private String jiBun;

     private String road;

     private String building;

     private String detail;

}
