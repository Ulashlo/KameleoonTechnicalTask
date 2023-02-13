package com.example.kameleoontechnicaltask.model;

import java.time.LocalDateTime;

public class QuoteTestBuilder {
    private Long id;
    private String content;
    private UserEntity userWhoCreated;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastUpdate;
    private Integer score;

    private QuoteTestBuilder() {
    }

    private QuoteTestBuilder(QuoteTestBuilder testBuilder) {
        this.id = testBuilder.id;
        this.content = testBuilder.content;
        this.userWhoCreated = testBuilder.userWhoCreated;
        this.dateOfCreation = testBuilder.dateOfCreation;
        this.dateOfLastUpdate = testBuilder.dateOfLastUpdate;
        this.score = testBuilder.score;
    }

    public static QuoteTestBuilder aQuote() {
        return new QuoteTestBuilder();
    }

    public QuoteTestBuilder withId(Long id) {
        final var copy = new QuoteTestBuilder(this);
        copy.id = id;
        return copy;
    }

    public QuoteTestBuilder withContent(String content) {
        final var copy = new QuoteTestBuilder(this);
        copy.content = content;
        return copy;
    }

    public QuoteTestBuilder withUserWhoCreated(UserEntity userWhoCreated) {
        final var copy = new QuoteTestBuilder(this);
        copy.userWhoCreated = userWhoCreated;
        return copy;
    }

    public QuoteTestBuilder withDateOfCreation(LocalDateTime dateOfCreation) {
        final var copy = new QuoteTestBuilder(this);
        copy.dateOfCreation = dateOfCreation;
        return copy;
    }

    public QuoteTestBuilder withDateOfLastUpdate(LocalDateTime dateOfLastUpdate) {
        final var copy = new QuoteTestBuilder(this);
        copy.dateOfLastUpdate = dateOfLastUpdate;
        return copy;
    }

    public QuoteTestBuilder withScore(Integer score) {
        final var copy = new QuoteTestBuilder(this);
        copy.score = score;
        return copy;
    }

    public Quote build() {
        return new Quote(
            id,
            content,
            userWhoCreated,
            dateOfCreation,
            dateOfLastUpdate,
            score
        );
    }
}
