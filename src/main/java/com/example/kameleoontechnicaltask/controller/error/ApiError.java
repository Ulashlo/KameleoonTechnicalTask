package com.example.kameleoontechnicaltask.controller.error;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
@Schema(title = "Api error info")
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
