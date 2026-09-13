package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Review> findByMenuItemIdOrderByCreatedAtDesc(Long menuItemId);

    boolean existsByUserIdAndMenuItemId(
            Long userId,
            Long menuItemId
    );

}
