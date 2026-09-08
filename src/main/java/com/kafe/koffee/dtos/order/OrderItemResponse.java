package com.kafe.koffee.dtos.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal) {

}
