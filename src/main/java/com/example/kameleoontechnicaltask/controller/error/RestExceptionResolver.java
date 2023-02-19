package com.example.kameleoontechnicaltask.controller.error;

import com.example.kameleoontechnicaltask.exceprion.CustomConstraintViolationException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionResolver extends ResponseEntityExceptionHandler {
    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        return innerHandleException(HttpStatus.FORBIDDEN, "Access denied!", ex);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ApiError> handleWrongCredentialsException(NoSuchElementException ex) {
        return innerHandleException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ApiError> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return innerHandleException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        return innerHandleException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        return innerHandleException(
            HttpStatus.BAD_REQUEST,
            "Request parameters are wrong!",
            ex
        );
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final var message = "Request body is wrong!";
        log.error(message, ex);
        final var error = ApiError.createError(HttpStatus.valueOf(status.value()), message);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(CustomConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiError> handleCustomConstraintViolationException(CustomConstraintViolationException ex) {
        return innerHandleException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiError> handleThrowable(Exception ex) {
        return innerHandleException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Unknown exception! \n%s \n%s", ex.getClass().getName(), ex.getMessage()),
            ex
        );
    }

    private ResponseEntity<ApiError> innerHandleException(HttpStatus status, String message, Exception ex) {
        log.error(message, ex);
        final var error = ApiError.createError(status, message);
        return new ResponseEntity<>(error, status);
    }
}
