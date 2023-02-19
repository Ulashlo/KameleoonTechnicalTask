package com.example.kameleoontechnicaltask.controller.dto.quote;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Quote info for create")
public class QuoteInfoForCreateDTO {
    @NotBlank
    private String content;
}
