package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;

import java.util.NoSuchElementException;

/**
 * Service provide quote voting operations
 */
public interface VoteService {
    /**
     * Voting due to {@linkplain VoteType}.
     *
     * @param quoteId  id of quote for voting
     * @param voteType upvote or downvote
     * @throws NoSuchElementException if there are no quote with given id
     */
    void vote(Long quoteId, VoteType voteType);
}
