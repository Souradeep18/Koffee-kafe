package com.kafe.koffee.dtos.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.kafe.koffee.enums.OrderStatus;

public record OrderResponse(        
		Long orderId,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemResponse> items) {

}
