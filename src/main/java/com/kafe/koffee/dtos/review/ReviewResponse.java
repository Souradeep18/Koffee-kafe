package com.kafe.koffee.dtos.review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long menuItemId,
        String menuItemName,
        String userName,
        Integer rating,
        String comment,
        LocalDateTime createdAt
        ) {

}
