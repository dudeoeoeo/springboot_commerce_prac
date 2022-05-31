package com.example.commerce.business.item.repository;

import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.item.dao.ItemDAO;
import com.example.commerce.business.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemDAO {

//    @Query(value = "SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.deleteYn = 0")
    Optional<Item> findByCategory(Category category);
}
