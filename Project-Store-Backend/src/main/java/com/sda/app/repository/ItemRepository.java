package com.sda.app.repository;

import com.sda.app.entity.Category;
import com.sda.app.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository <Item, Integer> {
    List<Item> findAllByCategory(Category category);
}
