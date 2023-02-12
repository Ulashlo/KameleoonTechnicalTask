package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
