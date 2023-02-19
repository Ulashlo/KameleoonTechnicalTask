package com.example.kameleoontechnicaltask.controller.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "User's short info")
public class UserLinkDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;
}
