package com.rodrigo.login.exception;

import java.time.LocalDateTime;

public record StandError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String path
) {
}
