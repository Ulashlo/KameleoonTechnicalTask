package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.VoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class VoteServiceTestConfiguration {
    @MockBean
    public UserService userService;

    @Bean
    public VoteService voteService(VoteRepository voteRepository,
                                   QuoteRepository quoteRepository,
                                   UserService userService) {
        return new VoteServiceImpl(voteRepository, quoteRepository, userService);
    }
}
