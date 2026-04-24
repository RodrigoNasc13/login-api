package com.rodrigo.login.user.dto;

import java.util.UUID;

public record AuthUserDTO(UUID id, String name, String email) {
}
