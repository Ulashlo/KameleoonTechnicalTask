package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@DisplayName("QuoteRepository: findQuoteIdsWithPagination unit test suit")
class QuoteRepositoryTest_findQuoteIdsWithPagination {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    private UserEntity defaultUser;
    private LocalDateTime defaultDate;
    private PageRequest pageRequestOrderedByDateOfCreation;

    @BeforeEach
    void init() {
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser()
                .withName("user1")
                .build()
        );
        defaultDate = LocalDateTime.of(2022, 2, 18, 16, 11);
        pageRequestOrderedByDateOfCreation = PageRequest.of(
            1,
            3,
            Sort.by("dateOfCreation").descending()
        );
    }

    @Test
    @DisplayName("Should return empty list if there are no quotes")
    void shouldReturnEmptyListIfThereAreNoQuotes() {
        final var quotes = quoteRepository.findQuoteIdsWithPagination(
            pageRequestOrderedByDateOfCreation
        );

        assertTrue(quotes.isEmpty());
    }

    @Test
    @DisplayName("Should return correct quote ids")
    void shouldReturnCorrectQuoteIds() {
        final var quoteDraft = aQuote().withUserWhoCreated(defaultUser);
        final var firstQuote = quoteRepository.saveAndFlush(
            quoteDraft
                .withDateOfCreation(defaultDate.minusDays(2))
                .build()
        );
        final var secondQuote = quoteRepository.saveAndFlush(
            quoteDraft
                .withDateOfCreation(defaultDate.minusDays(3))
                .build()
        );
        final var thirdQuote = quoteRepository.saveAndFlush(
            quoteDraft
                .withDateOfCreation(defaultDate.minusDays(3).minusHours(1))
                .build()
        );
        quoteRepository.saveAllAndFlush(
            List.of(
                quoteDraft
                    .withScore(1)
                    .withDateOfCreation(defaultDate)
                    .build(),
                quoteDraft
                    .withScore(-10)
                    .withDateOfCreation(defaultDate)
                    .build(),
                quoteDraft
                    .withScore(5)
                    .withDateOfCreation(defaultDate.minusDays(1))
                    .build(),
                quoteDraft
                    .withScore(4)
                    .withDateOfCreation(defaultDate.minusDays(4))
                    .build(),
                quoteDraft
                    .withScore(10)
                    .withDateOfCreation(defaultDate.minusDays(4))
                    .build()
            )
        );

        final var quotes = quoteRepository.findQuoteIdsWithPagination(
            pageRequestOrderedByDateOfCreation
        );

        assertEquals(3, quotes.size());
        assertEquals(firstQuote.getId(), quotes.get(0));
        assertEquals(secondQuote.getId(), quotes.get(1));
        assertEquals(thirdQuote.getId(), quotes.get(2));
    }
}