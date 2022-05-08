package com.example.commerce.business.user.service;

import com.example.commerce.business.user.dto.request.AddressAddRequest;

public interface AddressService {

    void addAddress(AddressAddRequest request, Long userId);
}
