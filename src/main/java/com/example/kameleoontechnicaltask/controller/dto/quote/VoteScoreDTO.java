package com.example.kameleoontechnicaltask.controller.dto.quote;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteScoreDTO {
    @NotNull
    private Integer score;

    @NotNull
    private LocalDate date;
}
