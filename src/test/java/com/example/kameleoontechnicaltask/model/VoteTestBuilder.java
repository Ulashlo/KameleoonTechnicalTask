package com.example.kameleoontechnicaltask.model;

import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;

import java.time.LocalDateTime;

import static com.example.kameleoontechnicaltask.model.InnerVoteType.UPVOTE;

public class VoteTestBuilder {
    private Long id;
    private InnerVoteType type = UPVOTE;
    private UserEntity userWhoCreated;
    private Quote quote;
    private LocalDateTime dateOfVoting =
        LocalDateTime.of(2022, 2, 18, 10, 10);

    private VoteTestBuilder() {
    }

    private VoteTestBuilder(VoteTestBuilder testBuilder) {
        this.id = testBuilder.id;
        this.type = testBuilder.type;
        this.userWhoCreated = testBuilder.userWhoCreated;
        this.quote = testBuilder.quote;
        this.dateOfVoting = testBuilder.dateOfVoting;
    }

    public static VoteTestBuilder aVote() {
        return new VoteTestBuilder();
    }

    public VoteTestBuilder withId(Long id) {
        final var copy = new VoteTestBuilder(this);
        copy.id = id;
        return copy;
    }

    public VoteTestBuilder withType(InnerVoteType type) {
        final var copy = new VoteTestBuilder(this);
        copy.type = type;
        return copy;
    }

    public VoteTestBuilder withUserWhoCreated(UserEntity userWhoCreated) {
        final var copy = new VoteTestBuilder(this);
        copy.userWhoCreated = userWhoCreated;
        return copy;
    }

    public VoteTestBuilder withQuote(Quote quote) {
        final var copy = new VoteTestBuilder(this);
        copy.quote = quote;
        return copy;
    }

    public VoteTestBuilder withDateOfVoting(LocalDateTime dateOfVoting) {
        final var copy = new VoteTestBuilder(this);
        copy.dateOfVoting = dateOfVoting;
        return copy;
    }

    public Vote build() {
        return new Vote(
            id,
            type,
            userWhoCreated,
            quote,
            dateOfVoting
        );
    }
}
