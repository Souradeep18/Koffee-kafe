package com.kafe.koffee.dtos.auth;

public record RegisterRequest(
        String name,
        String email,
        String password
        ) {

}
