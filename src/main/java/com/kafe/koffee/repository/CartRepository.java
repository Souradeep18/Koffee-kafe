package com.kafe.koffee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
	
	Optional<Cart> findByUserId(Long userId);
	Optional<Cart> findByUserIdAndId(Long userId,Long cartId);

}
