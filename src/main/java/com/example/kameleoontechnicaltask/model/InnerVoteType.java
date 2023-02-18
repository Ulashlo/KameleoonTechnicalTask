package com.example.kameleoontechnicaltask.model;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * In order for the user's old votes for a particular quote to be displayed on the chart,
 * I added separate values for changing the vote. For example, if user chose upvote yesterday
 * we save 1 point of score (UPVOTE), and if he changes his vote to downvote today we save -2
 * point of score (UPVOTE_TO_DOWNVOTE). As a result we have 1 score for yesterday and -1 score
 * fot today on the chart. We may also use this data to analyze user behavior.
 */
@Getter
@AllArgsConstructor
public enum InnerVoteType {
    UPVOTE(VoteType.UPVOTE, 1),
    DOWNVOTE_TO_UPVOTE(VoteType.UPVOTE, 2),
    DOWNVOTE(VoteType.DOWNVOTE, -1),
    UPVOTE_TO_DOWNVOTE(VoteType.DOWNVOTE, -2),
    UPVOTE_TO_NO_VOTE(VoteType.NO_VOTE, -1),
    DOWNVOTE_TO_NO_VOTE(VoteType.NO_VOTE, 1);

    private final VoteType equivalent;
    private final Integer scoreDifference;
}
