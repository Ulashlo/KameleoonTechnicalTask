package com.example.kameleoontechnicaltask.service.user;

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
        final var badCredentialsException = new BadCredentialsException("Wrong login or password");
        final var user = userRepository.findByName(name).orElseThrow(() -> badCredentialsException);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return getAccountInfo(user, password);
        }
        throw badCredentialsException;
    }

    @Override
    public UserEntity getCurrentUser() {
        final var userNotAuthenticatedException = new AuthenticationCredentialsNotFoundException(
            "User has not been authenticated yet"
        );
        final var authentication =
            ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> userNotAuthenticatedException);
        if (!(authentication.getPrincipal() instanceof final UserDetails userDetails)) {
            throw userNotAuthenticatedException;
        }
        return userRepository.findByName(userDetails.getUsername()).orElseThrow(
            () -> new NoSuchElementException(format("No user with name = %s", userDetails.getUsername()))
        );
    }

    private AccountInfoDTO getAccountInfo(UserEntity user, String rawPassword) {
        return new AccountInfoDTO(
            user.getName(),
            user.getEmail(),
            accessTokenService.generateAccessToken(user.getName(), rawPassword),
            user.getDateOfCreation()
        );
    }
}
