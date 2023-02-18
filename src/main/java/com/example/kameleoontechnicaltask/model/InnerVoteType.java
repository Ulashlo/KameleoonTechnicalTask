package com.example.kameleoontechnicaltask.model;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InnerVoteType {
    UPVOTE(VoteType.UPVOTE, 1),
    CHANGE_TO_UPVOTE(VoteType.UPVOTE, 2),
    DOWNVOTE(VoteType.DOWNVOTE, -1),
    CHANGE_TO_DOWNVOTE(VoteType.DOWNVOTE, -2);

    private final VoteType equivalent;
    private final Integer scoreDifference;
}
