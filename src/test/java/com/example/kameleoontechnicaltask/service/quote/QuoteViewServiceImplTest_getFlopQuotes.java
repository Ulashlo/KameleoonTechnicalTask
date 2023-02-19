package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteWithScoreDynamicDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteScoreDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.model.InnerVoteType;
import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import com.example.kameleoontechnicaltask.repository.VoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static com.example.kameleoontechnicaltask.model.VoteTestBuilder.aVote;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = QuoteViewServiceTestConfiguration.class)
@DisplayName("QuoteViewServiceImpl: getFlopQuotes unit test suit")
class QuoteViewServiceImplTest_getFlopQuotes {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private QuoteViewService quoteViewService;
    @Autowired
    private UserService userService;

    private UserEntity defaultUser;
    private LocalDateTime defaultDate;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(aUser().build());
        defaultDate = LocalDateTime.now();
        Mockito.when(userService.getCurrentUser()).thenReturn(Optional.of(defaultUser));
    }

    @Test
    @DisplayName("Should return correct result")
    void shouldReturnCorrectResult() {
        final var user1 = userRepository.saveAndFlush(
            aUser()
                .withName("user1")
                .build()
        );
        final var user2 = userRepository.saveAndFlush(
            aUser()
                .withName("user2")
                .build()
        );
        final var quoteDraft = aQuote()
            .withUserWhoCreated(defaultUser)
            .withDateOfCreation(defaultDate);
        final var quote1 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(-1)
                .build()
        );
        final var quote2 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user1)
                .withScore(-2)
                .build()
        );
        final var quote3 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user2)
                .withScore(1)
                .build()
        );
        final var quote4 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user2)
                .withScore(1)
                .build()
        );
        final var vote1Draft = aVote().withQuote(quote1);
        final var vote2Draft = aVote().withQuote(quote2);
        final var vote3Draft = aVote().withQuote(quote3);
        final var vote4Draft = aVote().withQuote(quote4);
        voteRepository.saveAllAndFlush(
            List.of(
                vote1Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user1)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(5))
                    .withUserWhoCreated(defaultUser)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.DOWNVOTE_TO_UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(defaultUser)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.UPVOTE_TO_DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withUserWhoCreated(defaultUser)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withUserWhoCreated(user1)
                    .build(),
                vote4Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withUserWhoCreated(user1)
                    .build()
            )
        );

        final var result = quoteViewService.getFlopQuotes(2);

        assertEquals(2, result.size());

        final var first = result.get(0);
        final var second = result.get(1);
        checkQuoteInfo(second, quote1, VoteType.NO_VOTE);
        checkQuoteInfo(first, quote2, VoteType.DOWNVOTE);
        final var firstScoreChangeDynamics = first.getScoreChangeDynamics();
        final var secondScoreChangeDynamics = second.getScoreChangeDynamics();
        assertEquals(1, secondScoreChangeDynamics.size());
        assertEquals(6, firstScoreChangeDynamics.size());
        checkQuoteDateScore(secondScoreChangeDynamics.get(0), defaultDate.toLocalDate(), -1);
        checkQuoteDateScore(firstScoreChangeDynamics.get(0), defaultDate.minusDays(5).toLocalDate(), -1);
        checkQuoteDateScore(firstScoreChangeDynamics.get(1), defaultDate.minusDays(4).toLocalDate(), -1);
        checkQuoteDateScore(firstScoreChangeDynamics.get(2), defaultDate.minusDays(3).toLocalDate(), 1);
        checkQuoteDateScore(firstScoreChangeDynamics.get(3), defaultDate.minusDays(2).toLocalDate(), -2);
        checkQuoteDateScore(firstScoreChangeDynamics.get(4), defaultDate.minusDays(1).toLocalDate(), -2);
        checkQuoteDateScore(firstScoreChangeDynamics.get(5), defaultDate.toLocalDate(), -2);
    }

    private void checkQuoteInfo(QuoteWithScoreDynamicDTO quoteWithScoreDynamicDTO, Quote quote, VoteType usersVoteType) {
        assertEquals(quote.getId(), quoteWithScoreDynamicDTO.getId());
        assertEquals(quote.getScore(), quoteWithScoreDynamicDTO.getScore());
        assertEquals(quote.getContent(), quoteWithScoreDynamicDTO.getContent());
        assertEquals(quote.getUserWhoCreated().getId(), quoteWithScoreDynamicDTO.getUserWhoCreated().getId());
        assertEquals(usersVoteType, quoteWithScoreDynamicDTO.getCurrentUserVoteType());
    }

    private void checkQuoteDateScore(VoteScoreDTO voteScoreDTO, LocalDate date, Integer score) {
        assertEquals(score, voteScoreDTO.getScore());
        assertEquals(date, voteScoreDTO.getDate());
    }
}