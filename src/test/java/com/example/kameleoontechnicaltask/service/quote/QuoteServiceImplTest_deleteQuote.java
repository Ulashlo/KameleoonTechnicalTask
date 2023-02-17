package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.NoSuchElementException;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = QuoteServiceTestConfiguration.class)
@DisplayName("QuoteServiceImpl: deleteQuote unit test suit")
class QuoteServiceImplTest_deleteQuote {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteService quoteService;

    private UserEntity defaultUser;

    @BeforeEach
    void init() {
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser().build()
        );
    }

    @Test
    @DisplayName("Should throw exception if there are no quotes with given id")
    void shouldThrowExceptionIfThereAreNoQuotesWithGivenId() {
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );

        assertThrows(
            NoSuchElementException.class,
            () -> quoteService.deleteQuote(quote.getId() * 5)
        );
    }

    @Test
    @DisplayName("Should delete quote correctly")
    void shouldUpdateQuoteCorrectly() {
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );

        quoteService.deleteQuote(quote.getId());

        final var savedQuoteOpt = quoteRepository.findById(quote.getId());
        assertTrue(savedQuoteOpt.isEmpty());
    }
}