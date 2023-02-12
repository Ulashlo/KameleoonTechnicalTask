package com.example.kameleoontechnicaltask.controller.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionResolver extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ApiError> handleWrongCredentialsException(NoSuchElementException ex) {
        return innerHandleException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ApiError> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return innerHandleException(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        return innerHandleException(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        return innerHandleException(
            HttpStatus.BAD_REQUEST,
            "Request parameters are wrong!"
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiError> handleThrowable(Exception ex) {
        return innerHandleException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Unknown exception! \n%s \n%s", ex.getClass().getName(), ex.getMessage())
        );
    }

    private ResponseEntity<ApiError> innerHandleException(HttpStatus status, String message) {
        final var error = ApiError.createError(status, message);
        return new ResponseEntity<>(error, status);
    }
}
