package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.model.InnerVoteType;
import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.model.QuoteTestBuilder;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.query.QuoteInfoWithUsersLastVote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static com.example.kameleoontechnicaltask.model.VoteTestBuilder.aVote;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: findQuotesInfoWithUsersLastVoteByIds unit test suit")
class QuoteRepositoryTest_findQuotesInfoWithUsersLastVoteByIds {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;

    private UserEntity defaultUser;
    private LocalDateTime defaultDate;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(aUser().build());
        defaultDate = LocalDateTime.of(2022, 2, 18, 16, 11);
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
        final var quoteDraft = QuoteTestBuilder.aQuote()
            .withUserWhoCreated(defaultUser)
            .withDateOfCreation(defaultDate);
        final var quote1 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(2)
                .build()
        );
        final var quote2 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user1)
                .withScore(2)
                .build()
        );
        final var quote3 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user2)
                .withScore(-1)
                .build()
        );
        final var quote4 = quoteRepository.saveAndFlush(
            quoteDraft
                .withUserWhoCreated(user2)
                .withScore(-1)
                .build()
        );
        final var vote1Draft = aVote().withQuote(quote1);
        final var vote2Draft = aVote().withQuote(quote2);
        final var vote3Draft = aVote().withQuote(quote3);
        final var vote4Draft = aVote().withQuote(quote4);
        voteRepository.saveAllAndFlush(
            List.of(
                vote1Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user1)
                    .build(),
                vote1Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user2)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(5))
                    .withUserWhoCreated(user1)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user2)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(5))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.UPVOTE_TO_DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(4))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE_TO_NO_VOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withUserWhoCreated(user2)
                    .build(),
                vote4Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user2)
                    .build()
            )
        );

        final var result = quoteRepository.findQuotesInfoWithUsersLastVoteByIds(
            List.of(quote2.getId(), quote3.getId(), quote4.getId()),
            user1.getId()
        );

        assertEquals(3, result.size());
        checkQuoteInfo(
            result,
            quote2,
            VoteType.UPVOTE
        );
        checkQuoteInfo(
            result,
            quote3,
            VoteType.NO_VOTE
        );
        checkQuoteInfo(
            result,
            quote4,
            VoteType.NO_VOTE
        );
    }

    private void checkQuoteInfo(List<QuoteInfoWithUsersLastVote> result, Quote quote, VoteType lastVoteType) {
        final var info = result.stream()
            .filter(q -> q.getId().equals(quote.getId()))
            .findFirst()
            .orElseThrow();
        assertEquals(quote.getId(), info.getId());
        assertEquals(quote.getContent(), info.getContent());
        assertEquals(quote.getScore(), info.getScore());
        assertEquals(quote.getUserWhoCreated().getId(), info.getUserWhoCreatedId());
        assertEquals(lastVoteType, info.getUsersLastVoteType());
    }
}