package com.kafe.koffee.dtos.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequest(
        String code,
        BigDecimal discountPercentage,
        BigDecimal minimumOrderAmount,
        LocalDateTime validFrom,
        LocalDateTime validUntil
        ) {

}
