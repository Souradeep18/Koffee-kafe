package com.kafe.koffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
	List<CartItem> findByCartId(Long cartId);
	
    Optional<CartItem> findByCartIdAndMenuItemId(Long cartId,Long menuItemId);

    Optional<CartItem> findByIdAndCartId(Long itemId,Long cartId);
    
    //deletes cartItem
    void deleteByCartId(Long cartId);
	

}
