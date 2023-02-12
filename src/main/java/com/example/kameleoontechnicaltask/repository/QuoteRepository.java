package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
