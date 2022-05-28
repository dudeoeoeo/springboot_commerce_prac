package com.example.commerce.business.item.repository;

import com.example.commerce.business.item.domain.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
}
