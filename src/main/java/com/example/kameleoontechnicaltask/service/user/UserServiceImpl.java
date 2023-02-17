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
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

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
    @Transactional(readOnly = true)
    public Optional<UserEntity> getCurrentUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) ||
            !(authentication.getPrincipal() instanceof final UserDetails userDetails)) {
            return Optional.empty();
        }
        final var user = userRepository.findByName(userDetails.getUsername()).orElseThrow(
            () -> new NoSuchElementException(format("No user with name = %s", userDetails.getUsername()))
        );
        return Optional.of(user);
    }

    @Override
    public UserEntity getCurrentAuthenticatedUser() {
        return getCurrentUser().orElseThrow(
            () -> new AuthenticationCredentialsNotFoundException(
                "User has not been authenticated yet"
            )
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