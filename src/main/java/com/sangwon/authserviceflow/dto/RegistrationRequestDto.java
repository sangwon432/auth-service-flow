package com.sangwon.authserviceflow.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDto (
        @NotBlank(message = "username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 to 20 characters")
        String username,

        @NotBlank(message = "email is required")
        @Size(message = "Please provide a valid email address")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, max = 30, message = "Password must be between 6 to 30 letters")
        String password
) {
}
