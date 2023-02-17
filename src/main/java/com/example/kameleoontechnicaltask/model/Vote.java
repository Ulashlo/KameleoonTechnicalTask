package com.example.kameleoontechnicaltask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType type;

    @ManyToOne
    @JoinColumn(name = "user_who_created_id", nullable = false)
    private UserEntity userWhoCreated;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private LocalDateTime dateOfVoting;

    public Vote(VoteType type, UserEntity userWhoCreated, Quote quote) {
        this.type = type;
        this.userWhoCreated = userWhoCreated;
        this.quote = quote;
        this.dateOfVoting = LocalDateTime.now();
    }
}
