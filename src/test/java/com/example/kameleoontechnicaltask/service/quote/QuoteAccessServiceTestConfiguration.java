package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QuoteAccessServiceTestConfiguration {
    @MockBean
    public UserService userService;

    @Bean
    public QuoteAccessService quoteAccessService(UserService userService,
                                                 QuoteRepository quoteRepository) {
        return new QuoteAccessServiceImpl(userService, quoteRepository);
    }
}
