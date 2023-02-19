package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.exceprion.CustomConstraintViolationException;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = UserServiceTestConfiguration.class)
@DisplayName("UserServiceImpl: createAccount unit test suit")
class UserServiceImplTest_createAccount {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserConstraintService userConstraintService;

    private final String encodedPassword = "encoded_password";

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        doNothing()
            .when(userConstraintService)
            .checkUserAccountCreate(any());

        final var user = userService.createAccount(
            new UserInfoForCreateDTO(
                "name",
                "email@mail.ru",
                "password"
            )
        );

        final var savedUserOpt = userRepository.findByName(user.getName());
        assertTrue(savedUserOpt.isPresent());
        final var savedUser = savedUserOpt.orElseThrow();
        assertEquals(savedUser.getName(), user.getName());
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertEquals(savedUser.getPassword(), encodedPassword);
    }

    @Test
    @DisplayName("Should throw exception if parameters not valid")
    void shouldThrowExceptionIfParametersNotValid() {
        doThrow(new CustomConstraintViolationException())
            .when(userConstraintService)
            .checkUserAccountCreate(any());

        assertThrows(
            CustomConstraintViolationException.class,
            () -> userConstraintService.checkUserAccountCreate(
                new UserInfoForCreateDTO(
                    "someName",
                    "someEmail@mail.ru",
                    "password"
                )
            )
        );
    }
}