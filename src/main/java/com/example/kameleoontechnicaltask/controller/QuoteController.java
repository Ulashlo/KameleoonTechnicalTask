package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
import com.example.kameleoontechnicaltask.service.QuoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping("/safe/quote")
    public void createQuote(@RequestBody
                            @Valid QuoteInfoForCreateDTO infoForCreate) {
        quoteService.createQuote(infoForCreate);
    }

    @PutMapping("/safe/quote/{id}")
    public void updateQuote(@RequestBody
                            @Valid QuoteInfoForUpdateDTO infoForUpdate,
                            @PathVariable("id")
                            @NotNull Long id) {
        quoteService.updateQuote(id, infoForUpdate);
    }

    @DeleteMapping("/safe/quote/{id}")
    public void updateQuote(@PathVariable("id")
                            @NotNull Long id) {
        quoteService.deleteQuote(id);
    }
}
