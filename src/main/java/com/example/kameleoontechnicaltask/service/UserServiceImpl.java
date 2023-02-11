package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.AccountInfoDTO;
import com.example.kameleoontechnicaltask.controller.dto.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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
                ),
                LocalDateTime.now()
            )
        );
        return getAccountInfo(user, infoForCreate.getPassword());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInfoDTO login(String name, String password) {
        final var user = userRepository.findByName(name).orElseThrow(
            () -> new NoSuchElementException(format("No user with name = %s", name))
        );
        if (passwordEncoder.matches(password, user.getPassword())) {
            return getAccountInfo(user, password);
        }
        throw new BadCredentialsException("Wrong password!");
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
