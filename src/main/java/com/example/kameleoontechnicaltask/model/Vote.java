package com.example.kameleoontechnicaltask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VoteType type;

    @ManyToOne
    @JoinColumn(name = "user_who_created_id", nullable = false)
    private UserEntity userWhoCreated;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private LocalDateTime dateOfCreation;

    public Vote(VoteType type, UserEntity userWhoCreated, Quote quote) {
        this.type = type;
        this.userWhoCreated = userWhoCreated;
        this.quote = quote;
        this.dateOfCreation = LocalDateTime.now();
    }
}
