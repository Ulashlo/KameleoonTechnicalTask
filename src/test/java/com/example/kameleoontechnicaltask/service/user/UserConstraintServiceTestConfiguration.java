package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserConstraintServiceTestConfiguration {
    @Bean
    public UserConstraintService userConstraintService(UserRepository userRepository) {
        return new UserConstraintServiceImpl(userRepository);
    }
}
