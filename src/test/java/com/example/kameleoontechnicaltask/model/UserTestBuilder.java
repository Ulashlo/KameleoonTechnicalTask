package com.example.kameleoontechnicaltask.model;

import java.time.LocalDateTime;

public class UserTestBuilder {
    private Long id;
    private String name = "name";
    private String email = "email@mail.ru";
    private String password = "encoded_password";
    private LocalDateTime dateOfCreation =
        LocalDateTime.of(2022, 2, 17, 20, 19);

    private UserTestBuilder() {
    }

    private UserTestBuilder(UserTestBuilder testBuilder) {
        this.id = testBuilder.id;
        this.name = testBuilder.name;
        this.email = testBuilder.email;
        this.dateOfCreation = testBuilder.dateOfCreation;
        this.password = testBuilder.password;
    }

    public static UserTestBuilder aUser() {
        return new UserTestBuilder();
    }

    public UserTestBuilder withId(Long id) {
        final var copy = new UserTestBuilder(this);
        copy.id = id;
        return copy;
    }

    public UserTestBuilder withName(String name) {
        final var copy = new UserTestBuilder(this);
        copy.name = name;
        return copy;
    }

    public UserTestBuilder withEmail(String email) {
        final var copy = new UserTestBuilder(this);
        copy.email = email;
        return copy;
    }

    public UserTestBuilder withPassword(String password) {
        final var copy = new UserTestBuilder(this);
        copy.password = password;
        return copy;
    }

    public UserTestBuilder withDateOfCreation(LocalDateTime dateOfCreation) {
        final var copy = new UserTestBuilder(this);
        copy.dateOfCreation = dateOfCreation;
        return copy;
    }

    public UserEntity build() {
        return new UserEntity(
            id,
            name,
            email,
            password,
            dateOfCreation
        );
    }
}
