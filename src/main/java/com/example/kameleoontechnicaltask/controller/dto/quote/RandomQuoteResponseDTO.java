package com.example.kameleoontechnicaltask.controller.dto.quote;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomQuoteResponseDTO {
    private Boolean isFound;
    private QuoteDTO randomQuote;

    public Optional<QuoteDTO> getRandomQuote() {
        return ofNullable(randomQuote);
    }

    public static RandomQuoteResponseDTO createEmptyResponse() {
        return new RandomQuoteResponseDTO(
            false,
            null
        );
    }

    public static RandomQuoteResponseDTO createResponse(QuoteDTO quote) {
        return new RandomQuoteResponseDTO(
            true,
            quote
        );
    }
}
