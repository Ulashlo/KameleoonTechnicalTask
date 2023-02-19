package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;

import java.util.NoSuchElementException;

import static com.example.kameleoontechnicaltask.model.QuoteTestBuilder.aQuote;
import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = QuoteAccessServiceTestConfiguration.class)
@DisplayName("QuoteAccessServiceImpl: hasAccessToEditOrDeleteQuote unit test suit")
class QuoteAccessServiceImplTest_hasAccessToEditOrDeleteQuote {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteAccessService quoteAccessService;
    @Autowired
    private UserService userService;
    @Mock
    private Authentication authentication;

    private UserEntity defaultUser;
    private AutoCloseable closeable;

    @BeforeEach
    void init() {
        closeable = openMocks(this);
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser().build()
        );
        when(userService.getUserFromAuthentication(authentication)).thenReturn(defaultUser);
    }

    @AfterEach
    void closeMocks() throws Exception {
        closeable.close();
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
            () -> quoteAccessService.hasAccessToEditOrDeleteQuote(authentication, quote.getId() + 5)
        );
    }

    @Test
    @DisplayName("Should forbid access")
    void shouldForbidAccess() {
        final var anotherUser = userRepository.saveAndFlush(
            aUser()
                .withName("anotherUser")
                .build()
        );
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(anotherUser)
                .build()
        );

        final var result =
            quoteAccessService.hasAccessToEditOrDeleteQuote(authentication, quote.getId());

        assertFalse(result);
    }

    @Test
    @DisplayName("Should permit access")
    void shouldPermitAccess() {
        final var quote = quoteRepository.saveAndFlush(
            aQuote()
                .withUserWhoCreated(defaultUser)
                .build()
        );

        final var result =
            quoteAccessService.hasAccessToEditOrDeleteQuote(authentication, quote.getId());

        assertTrue(result);
    }
}