package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByMenuItemId(Long menuItemId);

}
