package com.example.commerce.business.user.service;

import com.example.commerce.business.user.domain.Address;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.dto.request.AddressAddRequest;
import com.example.commerce.business.user.repository.AddressRepository;
import com.example.commerce.business.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addAddress(AddressAddRequest request, Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        final List<Address> addressList = addressRepository.findAllByUser(user);
        if (addressList.size() > 3) {
            throw new IllegalArgumentException("주소는 최대 3개까지 등록할 수 있습니다.");
        }
        final Address newAddress = Address.newAddress(user, request);
        addressRepository.save(newAddress);

    }
}
