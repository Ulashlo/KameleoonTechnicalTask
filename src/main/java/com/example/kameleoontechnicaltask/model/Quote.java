package com.example.kameleoontechnicaltask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
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

    public Quote(String content, UserEntity userWhoCreated) {
        this.content = content;
        this.userWhoCreated = userWhoCreated;
        this.dateOfCreation = LocalDateTime.now();
    }

    public void updateQuote(String newContent) {
        this.content = newContent;
        this.dateOfLastUpdate = LocalDateTime.now();
    }
}
