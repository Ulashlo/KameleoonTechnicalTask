package com.example.kameleoontechnicaltask.repository.query;

import java.time.LocalDate;

public interface QuoteScoreGroupByDate {
    Long getId();

    Integer getScore();

    LocalDate getDate();
}
