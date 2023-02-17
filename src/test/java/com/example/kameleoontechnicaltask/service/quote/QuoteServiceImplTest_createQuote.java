package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = QuoteServiceTestConfiguration.class)
@DisplayName("QuoteServiceImpl: createQuote unit test suit")
class QuoteServiceImplTest_createQuote {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private UserService userService;

    private UserEntity defaultUser;

    @BeforeEach
    void init() {
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser().build()
        );
        when(userService.getCurrentAuthenticatedUser()).thenReturn(defaultUser);
    }

    @Test
    @DisplayName("Should create quote correctly")
    void shouldCreateQuoteCorrectly() {
        final var content = "Quote!";

        quoteService.createQuote(
            new QuoteInfoForCreateDTO(content)
        );

        final var quotes = quoteRepository.findAll();
        assertEquals(1, quotes.size());
        final var savedQuote = quotes.get(0);
        assertEquals(content, savedQuote.getContent());
        assertEquals(defaultUser, savedQuote.getUserWhoCreated());
        assertEquals(0, savedQuote.getScore());
    }
}