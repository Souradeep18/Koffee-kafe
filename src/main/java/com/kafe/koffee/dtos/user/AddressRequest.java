package com.kafe.koffee.dtos.user;

public record AddressRequest(
        String addressLine,
        String city,
        String state,
        String postalCode,
        boolean defaultAddress
        ) {

}
