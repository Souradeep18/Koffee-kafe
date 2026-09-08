package com.kafe.koffee.dtos.auth;

public record LoginRequest(
        String email,
        String password
        ) {

}
