package com.example.commerce.business.user.repository;

import com.example.commerce.business.user.domain.Address;
import com.example.commerce.business.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);
}
