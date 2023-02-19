package com.example.kameleoontechnicaltask.controller.dto.quote;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "Response with random quote")
public class RandomQuoteResponseDTO {
    private Boolean isFound;
    private QuoteWithScoreDynamicDTO randomQuote;

    public Optional<QuoteWithScoreDynamicDTO> getRandomQuote() {
        return ofNullable(randomQuote);
    }

    public static RandomQuoteResponseDTO createEmptyResponse() {
        return new RandomQuoteResponseDTO(
            false,
            null
        );
    }

    public static RandomQuoteResponseDTO createResponse(QuoteWithScoreDynamicDTO quote) {
        return new RandomQuoteResponseDTO(
            true,
            quote
        );
    }
}
