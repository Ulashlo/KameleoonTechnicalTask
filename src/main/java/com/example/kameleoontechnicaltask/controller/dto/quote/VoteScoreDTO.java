package com.example.kameleoontechnicaltask.controller.dto.quote;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Score for a quote in certain date")
public class VoteScoreDTO {
    @NotNull
    private Integer score;

    @NotNull
    private LocalDate date;
}
