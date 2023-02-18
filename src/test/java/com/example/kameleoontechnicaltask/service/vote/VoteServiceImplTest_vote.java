package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.model.InnerVoteType;
import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.model.Vote;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import com.example.kameleoontechnicaltask.repository.VoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = VoteServiceTestConfiguration.class)
@DisplayName("VoteServiceImpl: vote unit test suit")
class VoteServiceImplTest_vote {
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserService userService;

    private UserEntity defaultUser;
    private Quote defaultQuote;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(aUser().build());
        defaultQuote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );
        when(userService.getCurrentAuthenticatedUser()).thenReturn(defaultUser);
    }

    @Test
    @DisplayName("Should throw exception if there are no quote with given id")
    void shouldThrowExceptionIfThereAreNoQuoteWithGivenId() {
        assertThrows(
            NoSuchElementException.class,
            () -> voteService.vote(defaultQuote.getId() * 5, VoteType.UPVOTE)
        );
    }

    @Test
    @DisplayName("Should vote correctly")
    void shouldVoteCorrectly() {
        voteService.vote(defaultQuote.getId(), VoteType.UPVOTE);

        final var voteList = voteRepository.findAll();
        assertEquals(1, voteList.size());
        final var vote = voteList.get(0);
        assertEquals(InnerVoteType.UPVOTE, vote.getType());
        final var savedQuote = quoteRepository.findById(defaultQuote.getId()).orElseThrow();
        assertEquals(1, savedQuote.getScore());
    }

    @Test
    @DisplayName("Should vote several times correctly")
    void shouldVoteSeveralTimesCorrectly() {
        voteService.vote(defaultQuote.getId(), VoteType.UPVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.UPVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.DOWNVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.UPVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.DOWNVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.NO_VOTE);
        voteService.vote(defaultQuote.getId(), VoteType.UPVOTE);
        voteService.vote(defaultQuote.getId(), VoteType.NO_VOTE);
        voteService.vote(defaultQuote.getId(), VoteType.NO_VOTE);
        voteService.vote(defaultQuote.getId(), VoteType.DOWNVOTE);


        final var voteList =
            voteRepository.findAll().stream()
                .sorted(Comparator.comparing(Vote::getDateOfVoting))
                .toList();
        assertEquals(8, voteList.size());
        assertEquals(InnerVoteType.UPVOTE, voteList.get(0).getType());
        assertEquals(InnerVoteType.UPVOTE_TO_DOWNVOTE, voteList.get(1).getType());
        assertEquals(InnerVoteType.DOWNVOTE_TO_UPVOTE, voteList.get(2).getType());
        assertEquals(InnerVoteType.UPVOTE_TO_DOWNVOTE, voteList.get(3).getType());
        assertEquals(InnerVoteType.DOWNVOTE_TO_NO_VOTE, voteList.get(4).getType());
        assertEquals(InnerVoteType.UPVOTE, voteList.get(5).getType());
        assertEquals(InnerVoteType.UPVOTE_TO_NO_VOTE, voteList.get(6).getType());
        assertEquals(InnerVoteType.DOWNVOTE, voteList.get(7).getType());
        final var savedQuote = quoteRepository.findById(defaultQuote.getId()).orElseThrow();
        assertEquals(-1, savedQuote.getScore());
    }
}