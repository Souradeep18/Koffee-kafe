package com.kafe.koffee.dtos.order;

public record OrderItemRequest(
        Long menuItemId,
        Integer quantity
        ) {

}
