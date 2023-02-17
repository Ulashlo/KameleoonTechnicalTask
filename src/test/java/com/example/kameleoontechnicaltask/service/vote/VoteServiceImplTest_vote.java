package com.example.kameleoontechnicaltask.service.vote;

import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import com.example.kameleoontechnicaltask.repository.VoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static com.example.kameleoontechnicaltask.model.UserTestBuilder.aUser;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = VoteServiceTestConfiguration.class)
@DisplayName("VoteServiceImpl: vote unit test suit")
class VoteServiceImplTest_vote {
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UserService userService;

    private UserEntity defaultUser;

    @BeforeEach
    void init() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
        defaultUser = userRepository.saveAndFlush(aUser().build());
    }
}