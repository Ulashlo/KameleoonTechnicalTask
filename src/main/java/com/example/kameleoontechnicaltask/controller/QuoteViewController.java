package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.service.quote.QuoteViewService;
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

    @GetMapping("/random")
    public RandomQuoteResponseDTO getRandomQuote() {
        return quoteViewService.getRandomQuote();
    }

    @GetMapping("/top")
    public List<QuoteDTO> getTopQuotes() {
        return quoteViewService.getTopQuotes(10);
    }

    @GetMapping("/flop")
    public List<QuoteDTO> getFlopQuotes() {
        return quoteViewService.getFlopQuotes(10);
    }

    @GetMapping("/last")
    public List<QuoteDTO> getLastQuotes() {
        return quoteViewService.getLastQuotes(10);
    }
}
