package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteWithScoreDynamicDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.service.quote.QuoteViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuoteViewController {
    private final QuoteViewService quoteViewService;

    @Operation(summary = "Get a random quote")
    @GetMapping("/quote/random")
    public RandomQuoteResponseDTO getRandomQuote() {
        return quoteViewService.getRandomQuote();
    }

    @Operation(summary = "Get top 10 quotes")
    @GetMapping("/quote/top")
    public List<QuoteWithScoreDynamicDTO> getTopQuotes() {
        return quoteViewService.getTopQuotes(10);
    }

    @Operation(summary = "Get flop 10 quotes")
    @GetMapping("/quote/flop")
    public List<QuoteWithScoreDynamicDTO> getFlopQuotes() {
        return quoteViewService.getFlopQuotes(10);
    }

    @Operation(summary = "Get last quotes")
    @GetMapping("/quote/last")
    public List<QuoteWithScoreDynamicDTO> getLastQuotes(@RequestParam
                                                        @PositiveOrZero Integer pageNumber,
                                                        @RequestParam
                                                        @Positive Integer pageSize) {
        return quoteViewService.getLastQuotes(pageNumber, pageSize);
    }

    @Operation(summary = "Get flop 10 quotes")
    @SecurityRequirement(name = "basic")
    @GetMapping("/safe/quote/my")
    public List<QuoteWithScoreDynamicDTO> getMyQuotes() {
        return quoteViewService.getMyQuotes();
    }
}
