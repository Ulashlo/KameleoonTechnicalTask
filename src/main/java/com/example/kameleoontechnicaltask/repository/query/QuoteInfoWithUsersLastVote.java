package com.example.kameleoontechnicaltask.repository.query;

import com.example.kameleoontechnicaltask.model.VoteType;

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

    VoteType getUsersLastVoteNullable();

    default Optional<VoteType> getUsersLastVote() {
        return ofNullable(getUsersLastVoteNullable());
    }
}
