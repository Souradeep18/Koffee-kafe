package com.kafe.koffee.dtos.menu;

import java.math.BigDecimal;

import com.kafe.koffee.enums.CoffeeType;

public record MenuItemRequest(
        String name,
        String description,
        BigDecimal price,
        CoffeeType coffeeType,
        Long categoryId,
        String imageUrl
		) {

}
