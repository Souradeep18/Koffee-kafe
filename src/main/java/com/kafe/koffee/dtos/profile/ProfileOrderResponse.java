package com.kafe.koffee.dtos.profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.kafe.koffee.enums.OrderStatus;

public record ProfileOrderResponse(
	       Long orderId,

	        OrderStatus status,

	        BigDecimal totalAmount,

	        LocalDateTime createdAt) {

}
