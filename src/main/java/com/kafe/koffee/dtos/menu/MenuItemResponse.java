package com.kafe.koffee.dtos.menu;

import java.math.BigDecimal;

import com.kafe.koffee.enums.CoffeeType;

public record MenuItemResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        CoffeeType coffeeType,
        String categoryName,
        boolean available,
        String imageUrl
        ) {

}
