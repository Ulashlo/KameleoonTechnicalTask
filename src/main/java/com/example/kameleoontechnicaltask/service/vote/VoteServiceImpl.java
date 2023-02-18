package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.model.InnerVoteType;
import com.example.kameleoontechnicaltask.model.Vote;
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
        final var user = userService.getCurrentAuthenticatedUser();
        final var voteOpt = voteRepository.findTopByUserWhoCreatedAndQuoteOrderByDateOfVotingDesc(user, quote);
        final var lastVoteType = voteOpt
            .map(Vote::getType)
            .map(InnerVoteType::getEquivalent)
            .orElse(VoteType.NO_VOTE);
        if (lastVoteType == voteType) {
            return;
        }
        final var resultVoteType = voteType.getEquivalentByLastVoteType(lastVoteType).orElseThrow(
            () -> new IllegalStateException(
                String.format(
                    "Impossible to change vote status form %s to %s",
                    lastVoteType.name(),
                    voteType.name()
                )
            )
        );
        quote.updateScore(resultVoteType);
        quoteRepository.saveAndFlush(quote);
        voteRepository.saveAndFlush(
            new Vote(resultVoteType, user, quote)
        );
    }
}
