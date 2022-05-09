package com.example.commerce.business.user.controller;

import com.example.commerce.business.user.dto.request.AddressAddRequest;
import com.example.commerce.business.user.dto.response.AddressResponse;
import com.example.commerce.common.dto.SuccessResponse;
import com.example.commerce.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends CommonUtil {

    @PostMapping("/add/address")
    public SuccessResponse addAddress(
            @Valid @RequestBody AddressAddRequest addRequest,
            BindingResult bindingResult,
            HttpServletRequest request)
    {
        final Long userId = getUserId(request);
        return SuccessResponse.of(HttpStatus.OK.value(), "주소를 추가했습니다.");
    }
}
