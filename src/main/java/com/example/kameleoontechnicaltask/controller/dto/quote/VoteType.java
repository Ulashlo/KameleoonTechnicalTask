package com.example.kameleoontechnicaltask.controller.dto.quote;

import com.example.kameleoontechnicaltask.model.InnerVoteType;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum VoteType {
    UPVOTE,
    DOWNVOTE,
    NO_VOTE;

    public Optional<InnerVoteType> getEquivalentByLastVoteType(VoteType lastVote) {
        final var innerVoteType = switch (lastVote) {
            case UPVOTE -> switch (this) {
                case DOWNVOTE -> InnerVoteType.UPVOTE_TO_DOWNVOTE;
                case UPVOTE -> null;
                case NO_VOTE -> InnerVoteType.UPVOTE_TO_NO_VOTE;
            };
            case DOWNVOTE -> switch (this) {
                case DOWNVOTE -> null;
                case UPVOTE -> InnerVoteType.DOWNVOTE_TO_UPVOTE;
                case NO_VOTE -> InnerVoteType.DOWNVOTE_TO_NO_VOTE;
            };
            case NO_VOTE -> switch (this) {
                case DOWNVOTE -> InnerVoteType.DOWNVOTE;
                case UPVOTE -> InnerVoteType.UPVOTE;
                case NO_VOTE -> null;
            };
        };
        return ofNullable(innerVoteType);
    }
}
