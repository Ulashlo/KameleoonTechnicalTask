package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.controller.dto.user.AccountInfoDTO;
import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service provides functionality for working with a user account
 */
public interface UserService {
    /**
     * Create a new account.
     *
     * @param infoForCreate contains user's information
     * @return new account's info with access token
     */
    AccountInfoDTO createAccount(UserInfoForCreateDTO infoForCreate);

    /**
     * Generate an access token if credentials are correct.
     *
     * @param name     user's name
     * @param password user's password
     * @return user's account's info with access token
     * @throws BadCredentialsException if password or username is wrong
     */
    AccountInfoDTO login(String name, String password);

    /**
     * Return authenticated user's info.
     *
     * @return user's info
     * @throws AuthenticationCredentialsNotFoundException if there are no authenticated users
     * @throws NoSuchElementException                     if there are no users with name from credentials
     */
    UserEntity getAuthenticatedCurrentUser();

    /**
     * Return current user's info, or empty if user not authenticated.
     *
     * @return user's info
     * @throws NoSuchElementException if there are no users with name from credentials
     */
    Optional<UserEntity> getCurrentUser();
}
