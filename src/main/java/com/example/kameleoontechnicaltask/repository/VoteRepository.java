package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByUserWhoCreatedAndQuoteOrderByDateOfVotingDesc(UserEntity userWhoCreated, Quote quote);
}
