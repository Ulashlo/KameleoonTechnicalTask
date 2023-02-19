package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteType;
import com.example.kameleoontechnicaltask.service.quote.QuoteService;
import com.example.kameleoontechnicaltask.service.vote.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@SecurityRequirement(name = "basic")
@RequestMapping("/safe/quote")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;
    private final VoteService voteService;

    @Operation(summary = "Create a new quote")
    @PostMapping
    public void createQuote(@RequestBody
                            @Valid QuoteInfoForCreateDTO infoForCreate) {
        quoteService.createQuote(infoForCreate);
    }

    @Operation(summary = "Update a quote")
    @PutMapping("/{id}")
    @PreAuthorize("@QuoteAccessService.hasAccessToEditOrDeleteQuote(authentication, #id)")
    public void updateQuote(@RequestBody
                            @Valid QuoteInfoForUpdateDTO infoForUpdate,
                            @PathVariable("id")
                            @NotNull Long id) {
        quoteService.updateQuote(id, infoForUpdate);
    }

    @Operation(summary = "Delete a quote")
    @DeleteMapping("/{id}")
    @PreAuthorize("@QuoteAccessService.hasAccessToEditOrDeleteQuote(authentication, #id)")
    public void deleteQuote(@PathVariable("id")
                            @NotNull Long id) {
        quoteService.deleteQuote(id);
    }

    @Operation(summary = "Upvote for a quote")
    @PutMapping("/{id}/upvote")
    public void upvote(@PathVariable("id")
                       @NotNull Long id) {
        voteService.vote(id, VoteType.UPVOTE);
    }

    @Operation(summary = "Downvote for a quote")
    @PutMapping("/{id}/downvote")
    public void downvote(@PathVariable("id")
                         @NotNull Long id) {
        voteService.vote(id, VoteType.DOWNVOTE);
    }

    @Operation(summary = "Cansel vote on a quote")
    @PutMapping("/{id}/noVote")
    public void noVote(@PathVariable("id")
                       @NotNull Long id) {
        voteService.vote(id, VoteType.NO_VOTE);
    }
}
