package com.example.commerce.business.user.service;

import com.example.commerce.business.user.domain.Address;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.dto.request.AddressAddRequest;
import com.example.commerce.business.user.dto.request.UpdateAddressRequest;
import com.example.commerce.business.user.dto.response.AddressResponse;
import com.example.commerce.business.user.repository.AddressRepository;
import com.example.commerce.business.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddress(Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        final List<Address> addressList = addressRepository.findAllByUserAndDeleteYn(user.get(), 0);
        return addressList
                .stream()
                .map(e -> objectMapper.convertValue(e, AddressResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AddressResponse addAddress(AddressAddRequest request, Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        final List<Address> addressList = addressRepository.findAllByUserAndDeleteYn(user, 0);
        if (addressList.size() > 3) {
            throw new IllegalArgumentException("주소는 최대 3개까지 등록할 수 있습니다.");
        }

        final Address newAddress = Address.newAddress(user, request);
        final Address savedAddress = addressRepository.save(newAddress);
        return objectMapper.convertValue(savedAddress, AddressResponse.class);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long addressId, UpdateAddressRequest request) {
        final Optional<Address> address = addressRepository.findById(addressId);
        if (address.isEmpty())
            throw new IllegalArgumentException("해당 주소를 찾을 수 없습니다.");
        address.get().updateAddress(request);
        final Address savedAddress = addressRepository.save(address.get());
        return objectMapper.convertValue(savedAddress, AddressResponse.class);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId, Long userId) {
        final Optional<Address> address = addressRepository.findById(addressId);
        if (address.isEmpty())
            throw new IllegalArgumentException("해당 주소를 찾을 수 없습니다.");
        if (address.get().getUser().getId() != userId)
            throw new BadCredentialsException("본인이 등록한 주소만 삭제 가능합니다.");
        address.get().deleteAddress();
        addressRepository.save(address.get());
    }
}
