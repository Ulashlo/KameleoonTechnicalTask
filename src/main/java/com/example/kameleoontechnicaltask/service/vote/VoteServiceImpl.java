package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.model.Vote;
import com.example.kameleoontechnicaltask.model.VoteType;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.VoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void vote(Long quoteId, VoteType voteType) {
        final var quote = quoteRepository.findById(quoteId).orElseThrow(
            () -> new NoSuchElementException(String.format("No quote with id = %s", quoteId))
        );
        final var user = userService.getCurrentUser();
        final var voteOpt = voteRepository.findByUserWhoCreatedAndQuote(user, quote);
        if (voteOpt.isEmpty()) {
            voteRepository.saveAndFlush(
                new Vote(voteType, user, quote)
            );
            return;
        }
        final var vote = voteOpt.orElseThrow();
        if (vote.getType() != voteType) {
            vote.updateVote(voteType);
            voteRepository.saveAndFlush(vote);
        }
    }
}
