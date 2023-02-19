package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.exceprion.CustomConstraintViolationException;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = UserConstraintServiceTestConfiguration.class)
@DisplayName("UserConstraintServiceImpl: checkUserAccountCreate unit test suit")
class UserConstraintServiceImplTest_checkUserAccountCreate {
    @Autowired
    private UserConstraintService userConstraintService;
    @Autowired
    private UserRepository userRepository;

    private UserEntity defaultUser;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(
            aUser()
                .withName("name")
                .withEmail("email@mail.ru")
                .build()
        );
    }

    @Test
    @DisplayName("Should throw exception if user with given name already exist")
    void shouldThrowExceptionIfUserWithGivenNameAlreadyExist() {
        assertThrows(
            CustomConstraintViolationException.class,
            () -> userConstraintService.checkUserAccountCreate(
                new UserInfoForCreateDTO(
                    defaultUser.getName(),
                    "someEmail@mail.ru",
                    "password"
                )
            )
        );
    }

    @Test
    @DisplayName("Should throw exception if user with given email already exist")
    void shouldThrowExceptionIfUserWithGivenEmailAlreadyExist() {
        assertThrows(
            CustomConstraintViolationException.class,
            () -> userConstraintService.checkUserAccountCreate(
                new UserInfoForCreateDTO(
                    "someName",
                    defaultUser.getEmail(),
                    "password"
                )
            )
        );
    }

    @Test
    @DisplayName("Should not throw exception")
    void shouldNotThrowException() {
        assertDoesNotThrow(
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