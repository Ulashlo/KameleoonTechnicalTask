package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = QuoteServiceTestConfiguration.class)
@DisplayName("QuoteServiceImpl: updateQuote unit test suit")
class QuoteServiceImplTest_updateQuote {
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
        final var newContent = "New quote!";
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );

        assertThrows(
            NoSuchElementException.class,
            () -> quoteService.updateQuote(
                quote.getId() * 5,
                new QuoteInfoForUpdateDTO(newContent)
            )
        );
    }

    @Test
    @DisplayName("Should update quote correctly")
    void shouldUpdateQuoteCorrectly() {
        final var newContent = "New quote!";
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );

        quoteService.updateQuote(
            quote.getId(),
            new QuoteInfoForUpdateDTO(newContent)
        );

        final var savedQuoteOpt = quoteRepository.findById(quote.getId());
        assertTrue(savedQuoteOpt.isPresent());
        final var savedQuote = savedQuoteOpt.orElseThrow();
        assertEquals(newContent, savedQuote.getContent());
        assertTrue(savedQuote.getDateOfLastUpdate().isPresent());
    }
}