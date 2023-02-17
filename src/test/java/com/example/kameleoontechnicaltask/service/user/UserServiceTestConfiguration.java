package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UserServiceTestConfiguration {
    @MockBean
    public PasswordEncoder passwordEncoder;

    @MockBean
    public AccessTokenService accessTokenService;

    @Bean
    public UserService userService(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder,
                                   AccessTokenService accessTokenService) {
        return new UserServiceImpl(
            userRepository,
            passwordEncoder,
            accessTokenService
        );
    }
}
