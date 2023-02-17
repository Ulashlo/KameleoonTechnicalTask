package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.NoSuchElementException;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = UserServiceTestConfiguration.class)
@DisplayName("UserServiceImpl: getCurrentAuthenticatedUser unit test suit")
class UserServiceImplTest_getCurrentAuthenticatedUser {
    private static final String USERNAME = "name";

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = USERNAME)
    @DisplayName("Should throw exception if there are no user with given name")
    void shouldThrowExceptionIfThereAreNoUserWithGivenName() {
        userRepository.saveAndFlush(
            aUser()
                .withName("another_name")
                .build()
        );

        assertThrows(
            NoSuchElementException.class,
            () -> userService.getCurrentAuthenticatedUser()
        );
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Should throw exception if there are no authorized users")
    void shouldThrowExceptionIfThereAreNoAuthorizedUsers() {
        userRepository.saveAndFlush(
            aUser()
                .withName(USERNAME)
                .build()
        );

        assertThrows(
            AuthenticationCredentialsNotFoundException.class,
            () -> userService.getCurrentAuthenticatedUser()
        );
    }

    @Test
    @WithMockUser(username = USERNAME)
    @DisplayName("Should return correct result")
    void shouldReturnCorrectResult() {
        userRepository.saveAndFlush(
            aUser()
                .withName(USERNAME)
                .build()
        );

        final var currentUser = userService.getCurrentAuthenticatedUser();

        assertEquals(USERNAME, currentUser.getName());
    }
}