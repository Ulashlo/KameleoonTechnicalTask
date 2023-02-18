package com.example.kameleoontechnicaltask.repository.query;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.model.InnerVoteType;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public interface QuoteInfoWithUsersLastVote {
    Long getId();

    String getContent();

    Long getUserWhoCreatedId();

    String getUserWhoCreatedName();

    String getUserWhoCreatedEmail();

    LocalDateTime getDateOfCreation();

    LocalDateTime getDateOfLastUpdateNullable();

    default Optional<LocalDateTime> getDateOfLastUpdate() {
        return ofNullable(getDateOfLastUpdateNullable());
    }

    Integer getScore();

    InnerVoteType getUsersLastVoteNullable();

    default VoteType getUsersLastVoteType() {
        return ofNullable(getUsersLastVoteNullable())
            .map(InnerVoteType::getEquivalent)
            .orElse(VoteType.NO_VOTE);
    }
}
