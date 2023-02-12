package com.example.kameleoontechnicaltask.controller.dto.quote;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuoteInfoForCreateDTO {
    @NotBlank
    private String content;
}
