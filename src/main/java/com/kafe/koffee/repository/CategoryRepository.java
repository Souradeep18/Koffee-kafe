package com.kafe.koffee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);

}
