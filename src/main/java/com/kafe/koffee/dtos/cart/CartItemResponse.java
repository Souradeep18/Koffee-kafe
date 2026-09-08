package com.kafe.koffee.dtos.cart;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal
        ) {

}
