package com.kafe.koffee.dtos.auth;

public record LoginResponse(
        String token,
        String role
        ) {

}
