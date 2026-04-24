package com.rodrigo.login.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rodrigo.login.user.model.UserRole;

public record UserResponse(UUID id, String name, String email, Boolean active, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {}
