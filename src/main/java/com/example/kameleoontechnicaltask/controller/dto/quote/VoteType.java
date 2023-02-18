package com.example.kameleoontechnicaltask.controller.dto.quote;

import com.example.kameleoontechnicaltask.model.InnerVoteType;

public enum VoteType {
    UPVOTE,
    DOWNVOTE,
    NO_VOTE;

    public InnerVoteType getEquivalentByLastVoteType(VoteType lastVote) {
        return switch (lastVote) {
            case UPVOTE -> switch (this) {
                case DOWNVOTE -> InnerVoteType.UPVOTE_TO_DOWNVOTE;
                case UPVOTE -> throw new IllegalStateException(
                    "Impossible to change vote status form UPVOTE to UPVOTE"
                );
                case NO_VOTE -> InnerVoteType.UPVOTE_TO_NO_VOTE;
            };
            case DOWNVOTE -> switch (this) {
                case DOWNVOTE -> throw new IllegalStateException(
                    "Impossible to change vote status form DOWNVOTE to DOWNVOTE"
                );
                case UPVOTE -> InnerVoteType.DOWNVOTE_TO_UPVOTE;
                case NO_VOTE -> InnerVoteType.DOWNVOTE_TO_NO_VOTE;
            };
            case NO_VOTE -> switch (this) {
                case DOWNVOTE -> InnerVoteType.DOWNVOTE;
                case UPVOTE -> InnerVoteType.UPVOTE;
                case NO_VOTE -> throw new IllegalStateException(
                    "Impossible to change vote status form NO_VOTE to NO_VOTE"
                );
            };
        };
    }
}
