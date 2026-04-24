package com.rodrigo.login.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "name cannot be blank")
        String name
) {}
