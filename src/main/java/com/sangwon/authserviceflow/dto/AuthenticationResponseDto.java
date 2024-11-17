package com.sangwon.authserviceflow.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationResponseDto (
        String accessToken
) {}

