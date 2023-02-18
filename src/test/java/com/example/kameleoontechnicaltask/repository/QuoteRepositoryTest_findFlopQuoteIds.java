package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Stream;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: findFlopQuoteIds unit test suit")
class QuoteRepositoryTest_findFlopQuoteIds {
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
    @DisplayName("Should return empty list if there are no quotes")
    void shouldReturnEmptyListIfThereAreNoQuotes() {
        final var quotes = quoteRepository.findFlopQuoteIds(10);

        assertTrue(quotes.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideQuotesCountAndLimitArguments")
    @DisplayName("Should return all existing quotes if their count less then limit")
    void shouldReturnAllExistingQuotesIfTheirCountLessThenLimit(Integer quotesCount, Integer limit) {
        for (int i = 0; i < quotesCount; i++) {
            quoteRepository.saveAndFlush(
                aQuote()
                    .withUserWhoCreated(defaultUser)
                    .withScore(i)
                    .build()
            );
        }

        final var quotes = quoteRepository.findFlopQuoteIds(limit);

        assertEquals(quotesCount, quotes.size());
    }

    @Test
    @DisplayName("Should return correct quote ids")
    void shouldReturnCorrectQuoteIds() {
        final var quoteDraft = aQuote().withUserWhoCreated(defaultUser);
        final var firstQuote = quoteRepository.saveAndFlush(
            quoteDraft.withScore(-2).build()
        );
        final var secondQuote = quoteRepository.saveAndFlush(
            quoteDraft.withScore(0).build()
        );
        final var thirdQuote = quoteRepository.saveAndFlush(
            quoteDraft.withScore(0).build()
        );
        quoteRepository.saveAllAndFlush(
            List.of(
                quoteDraft.withScore(1).build(),
                quoteDraft.withScore(1).build(),
                quoteDraft.withScore(2).build(),
                quoteDraft.withScore(2).build(),
                quoteDraft.withScore(10).build()
            )
        );

        final var quotes = quoteRepository.findFlopQuoteIds(3);

        assertEquals(3, quotes.size());
        assertTrue(quotes.contains(firstQuote.getId()));
        assertTrue(quotes.contains(secondQuote.getId()));
        assertTrue(quotes.contains(thirdQuote.getId()));
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