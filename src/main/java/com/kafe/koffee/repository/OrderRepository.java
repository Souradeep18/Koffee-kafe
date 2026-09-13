package com.kafe.koffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Order;
import com.kafe.koffee.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order,Long> {
	
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    //userId maybe null.Resulting in empty db qury
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserIdAndStatus(
            Long userId,
            OrderStatus status
    );
}
