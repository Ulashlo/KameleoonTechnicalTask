package com.example.kameleoontechnicaltask.controller.dto.quote;

import com.example.kameleoontechnicaltask.controller.dto.user.UserLinkDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String content;

    @Valid
    @NotNull
    private UserLinkDTO userWhoCreated;

    @NotNull
    private LocalDateTime dateOfCreation;

    private LocalDateTime dateOfLastUpdate;

    @NotNull
    private Integer score;

    @NotNull
    private UsersVote usersVote;

    @NotNull
    private List<VoteScoreDTO> scoreChangeDynamics;

    public Optional<LocalDateTime> getDateOfLastUpdate() {
        return ofNullable(dateOfLastUpdate);
    }
}
