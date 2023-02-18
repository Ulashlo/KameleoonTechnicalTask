package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.InnerVoteType;
import com.example.kameleoontechnicaltask.model.QuoteTestBuilder;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.query.QuoteScoreGroupByDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Comparator;

import java.util.List;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static com.example.kameleoontechnicaltask.model.VoteTestBuilder.aVote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: findQuotesScoreByIds unit test suit")
class QuoteRepositoryTest_findQuotesScoreByIds {
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
        final var user3 = userRepository.saveAndFlush(
            aUser()
                .withName("user3")
                .build()
        );
        final var quoteDraft = QuoteTestBuilder.aQuote()
            .withUserWhoCreated(defaultUser)
            .withDateOfCreation(defaultDate);
        final var quote1 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(3)
                .build()
        );
        final var quote2 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(-1)
                .build()
        );
        final var quote3 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(3)
                .build()
        );
        final var quote4 = quoteRepository.saveAndFlush(
            quoteDraft
                .withScore(0)
                .build()
        );
        final var vote1Draft = aVote().withQuote(quote1);
        final var vote2Draft = aVote().withQuote(quote2);
        final var vote3Draft = aVote().withQuote(quote3);
        voteRepository.saveAllAndFlush(
            List.of(
                vote1Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user1)
                    .build(),
                vote1Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user2)
                    .build(),
                vote1Draft
                    .withType(InnerVoteType.UPVOTE_TO_DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user1)
                    .build(),
                vote1Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user3)
                    .build(),
                vote1Draft
                    .withType(InnerVoteType.DOWNVOTE_TO_NO_VOTE)
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user1)
                    .build(),
                vote2Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user2)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .withUserWhoCreated(user3)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE_TO_UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user1)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.DOWNVOTE_TO_NO_VOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user2)
                    .build(),
                vote3Draft
                    .withType(InnerVoteType.UPVOTE_TO_NO_VOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .withUserWhoCreated(user3)
                    .build()
            )
        );

        final var result = quoteRepository.findQuotesScoreByIds(
            List.of(quote1.getId(), quote3.getId(), quote4.getId())
        );

        final var result1 = getFilteredResult(result, quote1.getId());
        final var result2 = getFilteredResult(result, quote2.getId());
        final var result3 = getFilteredResult(result, quote3.getId());
        final var result4 = getFilteredResult(result, quote4.getId());

        assertEquals(result1.size(), 3);
        assertTrue(result2.isEmpty());
        assertEquals(result3.size(), 2);
        assertTrue(result4.isEmpty());
        checkResult(
            result1.get(0),
            2,
            defaultDate.minusDays(3)
        );
        checkResult(
            result1.get(1),
            -1,
            defaultDate.minusDays(1)
        );
        checkResult(
            result1.get(2),
            1,
            defaultDate
        );
        checkResult(
            result3.get(0),
            -1,
            defaultDate.minusDays(3)
        );
        checkResult(
            result3.get(1),
            2,
            defaultDate.minusDays(1)
        );
    }

    private void checkResult(QuoteScoreGroupByDate result, Integer score, LocalDateTime date) {
        assertEquals(result.getScore(), score);
        assertEquals(result.getDate(), date.toLocalDate());
    }

    private List<QuoteScoreGroupByDate> getFilteredResult(List<QuoteScoreGroupByDate> result, Long quoteId) {
        return result.stream()
            .filter(res -> res.getId().equals(quoteId))
            .sorted(Comparator.comparing(QuoteScoreGroupByDate::getDate))
            .toList();
    }
}