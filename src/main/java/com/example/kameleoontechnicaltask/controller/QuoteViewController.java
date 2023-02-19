package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.service.quote.QuoteViewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteViewController {
    private final QuoteViewService quoteViewService;

    @Operation(summary = "Get a random quote")
    @GetMapping("/random")
    public RandomQuoteResponseDTO getRandomQuote() {
        return quoteViewService.getRandomQuote();
    }

    @Operation(summary = "Get top 10 quotes")
    @GetMapping("/top")
    public List<QuoteDTO> getTopQuotes() {
        return quoteViewService.getTopQuotes(10);
    }

    @Operation(summary = "Get flop 10 quotes")
    @GetMapping("/flop")
    public List<QuoteDTO> getFlopQuotes() {
        return quoteViewService.getFlopQuotes(10);
    }

    @Operation(summary = "Get last 10 quotes")
    @GetMapping("/last")
    public List<QuoteDTO> getLastQuotes() {
        return quoteViewService.getLastQuotes(10);
    }
}
