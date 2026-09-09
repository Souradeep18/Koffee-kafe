package com.kafe.koffee.dtos.profile;

import java.time.LocalDate;

import com.kafe.koffee.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfileUpdateRequest(
		@NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        String email,

        LocalDate dateOfBirth,

        Gender gender
        ) {

}
