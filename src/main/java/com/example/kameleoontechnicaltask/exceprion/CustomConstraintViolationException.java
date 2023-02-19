package com.example.kameleoontechnicaltask.exceprion;

public class CustomConstraintViolationException extends RuntimeException {
    public CustomConstraintViolationException() {
    }

    public CustomConstraintViolationException(String message) {
        super(message);
    }

    public CustomConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
