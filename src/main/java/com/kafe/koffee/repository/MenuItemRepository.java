package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.enums.CoffeeType;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findByAvailableTrue();

    List<MenuItem> findByCategoryId(Long categoryId);

    List<MenuItem> findByCoffeeTypeAndAvailableTrue(CoffeeType coffeeType);

    List<MenuItem> findTop5ByAvailableTrueOrderByIdAsc();

    List<MenuItem> findByNameContainingIgnoreCase(String name);
    
    


}
