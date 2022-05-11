package com.example.commerce.business.user.controller;

import com.example.commerce.business.user.dto.request.AddressAddRequest;
import com.example.commerce.business.user.dto.request.UpdateAddressRequest;
import com.example.commerce.business.user.service.AddressService;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends CommonUtil {

    private final AddressService addressService;

    @GetMapping("/address/list")
    public SuccessResponse getAddress(HttpServletRequest request) {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), addressService.getAddress(userId));
    }

    @PostMapping("/address/add")
    public SuccessResponse addAddress(
            @Valid @RequestBody AddressAddRequest addRequest,
            BindingResult bindingResult,
            HttpServletRequest request)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), "주소를 추가했습니다.", addressService.addAddress(addRequest, userId));
    }

    @PostMapping("/address/{addressId}/update")
    public SuccessResponse updateAddress (
            @Valid @RequestBody UpdateAddressRequest updateAddressRequest,
            BindingResult bindingResult,
            @PathVariable Long addressId,
            HttpServletRequest request)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), addressService.updateAddress(addressId, updateAddressRequest));
    }

    @PostMapping("/address/{addressId}/delete")
    public SuccessResponse deleteAddress (
            @PathVariable Long addressId,
            HttpServletRequest request
    )
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), "주소를 삭제했습니다.");
    }
}
