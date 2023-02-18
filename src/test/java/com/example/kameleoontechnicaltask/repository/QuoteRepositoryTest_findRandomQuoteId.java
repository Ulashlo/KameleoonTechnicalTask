package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Stream;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: findLastQuoteIds unit test suit")
class QuoteRepositoryTest_findRandomQuoteId {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    private UserEntity defaultUser;

    @BeforeEach
    void init() {
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser()
                .withName("user1")
                .build()
        );
    }

    @Test
    @DisplayName("Should return empty result if there are no quotes")
    void shouldReturnEmptyResultIfThereAreNoQuotes() {
        final var randomQuoteIdOpt = quoteRepository.findRandomQuoteId();

        assertTrue(randomQuoteIdOpt.isEmpty());
    }

    @Test
    @DisplayName("Should return random quote id")
    void shouldReturnRandomQuoteId() {
        final var quoteDraft = aQuote().withUserWhoCreated(defaultUser);
        quoteRepository.saveAllAndFlush(
            List.of(
                quoteDraft.withScore(1).build(),
                quoteDraft.withScore(1).build(),
                quoteDraft.withScore(2).build(),
                quoteDraft.withScore(2).build(),
                quoteDraft.withScore(10).build()
            )
        );

        final var randomQuoteIdOpt = quoteRepository.findRandomQuoteId();

        assertTrue(randomQuoteIdOpt.isPresent());
        final var randomQuoteId = randomQuoteIdOpt.orElseThrow();
        final var quotes = quoteRepository.findAll();
        assertTrue(quotes.stream().anyMatch(quote -> quote.getId().equals(randomQuoteId)));
    }

    static Stream<Arguments> provideQuotesCountAndLimitArguments() {
        return Stream.of(
            Arguments.of(1, 10),
            Arguments.of(5, 10),
            Arguments.of(5, 5),
            Arguments.of(0, 5)
        );
    }
}