package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.user.AccountInfoDTO;
import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;


    @Override
    @Transactional
    public AccountInfoDTO createAccount(UserInfoForCreateDTO infoForCreate) {
        final var user = userRepository.saveAndFlush(
            new UserEntity(
                infoForCreate.getName(),
                infoForCreate.getEmail(),
                passwordEncoder.encode(
                    infoForCreate.getPassword()
                )
            )
        );
        return getAccountInfo(user, infoForCreate.getPassword());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInfoDTO login(String name, String password) {
        final var user = findUserByName(name);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return getAccountInfo(user, password);
        }
        throw new BadCredentialsException("Wrong password!");
    }

    @Override
    public UserEntity getCurrentUser() {
        final var authentication =
            ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
                    "User has not been authenticated yet"
                ));
        final var userDetails = (UserDetails) authentication.getPrincipal();
        return findUserByName(userDetails.getUsername());
    }

    private AccountInfoDTO getAccountInfo(UserEntity user, String rawPassword) {
        return new AccountInfoDTO(
            user.getName(),
            user.getEmail(),
            accessTokenService.generateAccessToken(user.getName(), rawPassword),
            user.getDateOfCreation()
        );
    }

    private UserEntity findUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(
            () -> new NoSuchElementException(format("No user with name = %s", name))
        );
    }
}
