package com.kafe.koffee.dtos.profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProfileCouponResponse(
		    Long id,

	        String code,

	        BigDecimal discountPercentage,

	        BigDecimal minimumOrderAmount,

	        LocalDateTime validFrom,

	        LocalDateTime validUntil) {

}
