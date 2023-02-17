package com.example.kameleoontechnicaltask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_who_created_id", nullable = false)
    private UserEntity userWhoCreated;

    private LocalDateTime dateOfCreation;

    private LocalDateTime dateOfLastUpdate;

    private Integer score;

    public Quote(String content, UserEntity userWhoCreated) {
        this.content = content;
        this.userWhoCreated = userWhoCreated;
        this.dateOfCreation = LocalDateTime.now();
        this.score = 0;
    }

    public void updateQuote(String newContent) {
        this.content = newContent;
        this.dateOfLastUpdate = LocalDateTime.now();
    }

    public void updateScore(VoteType voteType) {
        switch (voteType) {
            case UPVOTE -> this.score += 1;
            case DOWNVOTE -> this.score -= 1;
        }
    }

    public Optional<LocalDateTime> getDateOfLastUpdate() {
        return ofNullable(dateOfLastUpdate);
    }
}
