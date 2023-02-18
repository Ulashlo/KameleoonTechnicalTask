package com.example.kameleoontechnicaltask.model;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
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

    public void updateScore(InnerVoteType voteType) {
        this.score += voteType.getScoreDifference();
    }

    public Optional<LocalDateTime> getDateOfLastUpdate() {
        return ofNullable(dateOfLastUpdate);
    }
}
