package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = UserServiceTestConfiguration.class)
@DisplayName("UserServiceImpl: login unit test suit")
class UserServiceImplTest_login {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccessTokenService accessTokenService;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should throw exception if username not found")
    void shouldThrowExceptionIfUsernameNotFound() {
        final var password = "password";
        when(passwordEncoder.matches(password, password)).thenReturn(true);
        userRepository.saveAndFlush(
            aUser()
                .withName("some_name")
                .withPassword(password)
                .build()
        );

        assertThrows(
            BadCredentialsException.class,
            () -> userService.login("another_name", password)
        );
    }

    @Test
    @DisplayName("Should throw exception if password not matches")
    void shouldThrowExceptionIfPasswordNotMatches() {
        final var name = "some_name";
        final var password = "password";
        when(passwordEncoder.matches(password, password)).thenReturn(false);
        userRepository.saveAndFlush(
            aUser()
                .withName(name)
                .withPassword(password)
                .build()
        );

        assertThrows(
            BadCredentialsException.class,
            () -> userService.login(name, password)
        );
    }

    @Test
    @DisplayName("Should return correct result")
    void shouldReturnCorrectResult() {
        final var name = "some_name";
        final var password = "password";
        final var token = "token";
        when(passwordEncoder.matches(password, password)).thenReturn(true);
        when(accessTokenService.generateAccessToken(name, password)).thenReturn(token);
        userRepository.saveAndFlush(
            aUser()
                .withName(name)
                .withPassword(password)
                .build()
        );

        final var result = userService.login(name, password);

        assertEquals(name, result.getName());
        assertEquals(token, result.getAccessToken());
    }
}