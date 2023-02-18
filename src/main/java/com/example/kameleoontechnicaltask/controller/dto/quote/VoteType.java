package com.example.kameleoontechnicaltask.controller.dto.quote;

import com.example.kameleoontechnicaltask.model.InnerVoteType;

public enum VoteType {
    UPVOTE,
    DOWNVOTE;

    public InnerVoteType getEquivalent(Boolean isFirstVote) {
        return switch (this) {
            case UPVOTE -> isFirstVote ? InnerVoteType.UPVOTE : InnerVoteType.CHANGE_TO_UPVOTE;
            case DOWNVOTE -> isFirstVote ? InnerVoteType.DOWNVOTE : InnerVoteType.CHANGE_TO_DOWNVOTE;
        };
    }
}
