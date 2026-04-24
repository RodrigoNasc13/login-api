package com.rodrigo.login.user.dto;

import java.time.LocalDateTime;

public record ActiveCountResponse(
        long activeUsersCount,
        LocalDateTime lastUserRegistrationDate
) {
}
