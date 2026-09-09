package com.kafe.koffee.dtos.profile;

public record ProfileAddressResponse(
		    Long id,

	        String addressLine,

	        String city,

	        String state,

	        String postalCode,

	        boolean defaultAddress) {

}
