package com.kafe.koffee.dtos.user;

public record AddressResponse(
        Long id,
        String addressLine,
        String city,
        String state,
        String postalCode,
        boolean defaultAddress
        ) {

}
