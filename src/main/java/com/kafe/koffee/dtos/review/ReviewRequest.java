package com.kafe.koffee.dtos.review;

public record ReviewRequest(
        Long menuItemId,
        Integer rating,
        String comment
        ) {

}
