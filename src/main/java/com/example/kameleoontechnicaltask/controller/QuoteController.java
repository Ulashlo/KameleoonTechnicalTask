package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.service.quote.QuoteService;
import com.example.kameleoontechnicaltask.service.vote.VoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/safe/quote")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;
    private final VoteService voteService;

    @PostMapping
    public void createQuote(@RequestBody
                            @Valid QuoteInfoForCreateDTO infoForCreate) {
        quoteService.createQuote(infoForCreate);
    }

    @PutMapping("/{id}")
    public void updateQuote(@RequestBody
                            @Valid QuoteInfoForUpdateDTO infoForUpdate,
                            @PathVariable("id")
                            @NotNull Long id) {
        quoteService.updateQuote(id, infoForUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable("id")
                            @NotNull Long id) {
        quoteService.deleteQuote(id);
    }

    @PutMapping("/{id}/upvote")
    public void upvote(@PathVariable("id")
                       @NotNull Long id) {
        voteService.vote(id, VoteType.UPVOTE);
    }

    @PutMapping("/{id}/downvote")
    public void downvote(@PathVariable("id")
                         @NotNull Long id) {
        voteService.vote(id, VoteType.DOWNVOTE);
    }

    @PutMapping("/{id}/noVote")
    public void noVote(@PathVariable("id")
                       @NotNull Long id) {
        voteService.vote(id, VoteType.NO_VOTE);
    }
}
