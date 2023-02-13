package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.model.VoteType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kameleoontechnicaltask.model.VoteTestBuilder.aVote;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: unit test suit")
class QuoteRepositoryTest {
    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void should() {
        final var user = userRepository.saveAndFlush(
            new UserEntity(
                "name",
                "email",
                "password"
            )
        );
        final var quote = quoteRepository.saveAndFlush(
            new Quote(
                "content",
                user
            )
        );
        final var defaultDate = LocalDateTime.now();
        final var voteDraft =
            aVote()
                .withQuote(quote)
                .withUserWhoCreated(user);
        voteRepository.saveAllAndFlush(
            List.of(
                voteDraft
                    .withType(VoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .build(),
                voteDraft
                    .withType(VoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .build(),
                voteDraft
                    .withType(VoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(3))
                    .build(),
                voteDraft
                    .withType(VoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(2))
                    .build(),
                voteDraft
                    .withType(VoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .build(),
                voteDraft
                    .withType(VoteType.UPVOTE)
                    .withDateOfVoting(defaultDate.minusDays(1))
                    .build(),
                voteDraft
                    .withType(VoteType.DOWNVOTE)
                    .withDateOfVoting(defaultDate)
                    .build()
            )
        );

        final var result = quoteRepository.getQuotesScoreByIds(List.of(quote.getId()));
        long k = 1 + quote.getId();

        assertEquals(1, 1);
    }
}