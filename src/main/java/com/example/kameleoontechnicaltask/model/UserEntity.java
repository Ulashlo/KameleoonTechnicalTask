package com.example.kameleoontechnicaltask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime dateOfCreation;

    public UserEntity(String name, String email, String password, LocalDateTime dateOfCreation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfCreation = dateOfCreation;
    }
}
