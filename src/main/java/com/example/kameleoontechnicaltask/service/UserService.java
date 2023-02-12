package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.user.AccountInfoDTO;
import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.NoSuchElementException;

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
     * @throws BadCredentialsException if password is wrong
     * @throws NoSuchElementException  if there are no user with given name
     */
    AccountInfoDTO login(String name, String password);

    /**
     * Return authenticated user's info.
     *
     * @return user's info
     * @throws AuthenticationCredentialsNotFoundException if there are no authenticated users
     * @throws NoSuchElementException if there are no users with given id
     */
    UserEntity getCurrentUser();
}
