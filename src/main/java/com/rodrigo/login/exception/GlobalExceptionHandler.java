package com.rodrigo.login.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandError> userNotFound(UserNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        StandError error = new StandError(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<StandError> inactiveUser(InactiveUserException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        StandError error = new StandError(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandError> handleGenericException(Exception ex, HttpServletRequest request) {

        ex.printStackTrace();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandError error = new StandError(
                LocalDateTime.now(),
                status.value(),
                "Erro Inesperado. Classe da Exceção: " + ex.getClass().getSimpleName() + " | Mensagem: " + ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request){

        StandError error = new StandError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Access denied. You dont have permission for this",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

    };

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandError> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {

        StandError error = new StandError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandError error = new StandError(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandError> hangleMethodsValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        StandError error = new StandError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }
}
