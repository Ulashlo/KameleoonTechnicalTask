package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.VoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static com.example.kameleoontechnicaltask.model.VoteTestBuilder.aVote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@DisplayName("VoteRepository: findTopByUserWhoCreatedAndQuoteOrderByDateOfVotingDesc unit test suit")
class VoteRepositoryTest_findTopByUserWhoCreatedAndQuoteOrderByDateOfVotingDesc {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private VoteRepository voteRepository;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return correct result")
    void shouldReturnCorrectResult() {
        final var defaultDate = LocalDateTime.of(2022, 2, 18, 10, 10);
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
        final var quote11 = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(user1)
                .build()
        );
        final var quote12 = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(user1)
                .build()
        );
        final var quote2 = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(user2)
                .build()
        );
        final var voteDraft =
            aVote()
                .withUserWhoCreated(user1)
                .withQuote(quote11);
        final var lastVote = voteRepository.saveAndFlush(
            voteDraft
                .withDateOfVoting(defaultDate.minusDays(1))
                .withType(VoteType.DOWNVOTE)
                .build()
        );
        voteRepository.saveAllAndFlush(
            List.of(
                aVote()
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user1)
                    .withQuote(quote12)
                    .build(),
                aVote()
                    .withDateOfVoting(defaultDate)
                    .withUserWhoCreated(user2)
                    .withQuote(quote2)
                    .build(),
                voteDraft
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .withType(VoteType.UPVOTE)
                    .build(),
                voteDraft
                    .withDateOfVoting(defaultDate.minusDays(4))
                    .withType(VoteType.DOWNVOTE)
                    .build(),
                voteDraft
                    .withDateOfVoting(defaultDate.minusDays(5))
                    .withType(VoteType.UPVOTE)
                    .build()
            )
        );

        final var resultOpt = voteRepository.findTopByUserWhoCreatedAndQuoteOrderByDateOfVotingDesc(
            user1, quote11
        );

        assertTrue(resultOpt.isPresent());
        final var result = resultOpt.orElseThrow();
        assertEquals(lastVote, result);
    }
}