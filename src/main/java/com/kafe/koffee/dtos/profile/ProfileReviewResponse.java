package com.kafe.koffee.dtos.profile;

import java.time.LocalDateTime;

public record ProfileReviewResponse(
		    Long id,

	        Long menuItemId,

	        String menuItemName,

	        Integer rating,

	        String comment,

	        LocalDateTime createdAt) {

}
