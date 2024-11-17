package com.sangwon.authserviceflow.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto (
        @NotBlank(message = "email is required")
        String email,

        @NotBlank(message = "password is required")
        String password
) {}
