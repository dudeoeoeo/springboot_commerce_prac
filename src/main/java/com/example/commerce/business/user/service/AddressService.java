package com.example.commerce.business.user.service;

import com.example.commerce.business.user.dto.request.AddressAddRequest;
import com.example.commerce.business.user.dto.request.UpdateAddressRequest;
import com.example.commerce.business.user.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    List<AddressResponse> getAddress(Long userId);
    AddressResponse addAddress(AddressAddRequest request, Long userId);
    AddressResponse updateAddress(Long addressId, UpdateAddressRequest request);
    void deleteAddress(Long addressId, Long userId);
}
