package com.sangwon.authserviceflow.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto (
        @NotBlank(message = "username is required")
        String username,

        @NotBlank(message = "password is required")
        String password
) {}
