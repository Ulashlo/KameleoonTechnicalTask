package com.example.kameleoontechnicaltask.controller.error;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
public class ApiError {
    @NotNull
    private OffsetDateTime errorDateTime;
    @NotNull
    private int status;
    private String message;

    public static ApiError createError(@NotNull HttpStatus status, String message) {
        return new ApiError(status, message);
    }

    protected ApiError(@NotNull HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.errorDateTime = OffsetDateTime.now();
    }
}
