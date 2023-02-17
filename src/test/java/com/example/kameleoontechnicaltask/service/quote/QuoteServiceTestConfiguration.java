package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QuoteServiceTestConfiguration {
    @MockBean
    public UserService userService;

    @Bean
    public QuoteService quoteService(QuoteRepository quoteRepository,
                                     UserService userService) {
        return new QuoteServiceImpl(quoteRepository, userService);
    }
}
