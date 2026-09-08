package com.kafe.koffee.dtos.cart;

public record CartItemRequest(
        Long menuItemId,
        Integer quantity
        ) {

}
