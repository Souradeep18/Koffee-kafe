package com.kafe.koffee.dtos.profile;

import java.math.BigDecimal;

public record BeverageRecommendationResponse(

        Long menuItemId,

        String name,

        String description,

        BigDecimal price,

        String reason
        ) {

}
