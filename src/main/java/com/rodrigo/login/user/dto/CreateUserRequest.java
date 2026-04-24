package com.rodrigo.login.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.context.annotation.Bean;

public record CreateUserRequest(
        @NotBlank(message = "name cannot be null or empty")
        String name,

        @NotBlank(message = "Email cannot be null or empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be null or empty")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!*]).*$", message = "Password must contain at least one uppercase letter and one special character")
        String password
) {}
