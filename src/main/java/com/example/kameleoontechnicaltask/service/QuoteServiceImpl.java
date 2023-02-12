package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void createQuote(QuoteInfoForCreateDTO infoForCreate) {
        final var user = userService.getCurrentUser();
        quoteRepository.saveAndFlush(
            new Quote(
                infoForCreate.getContent(),
                user
            )
        );
    }

    @Override
    @Transactional
    public void updateQuote(Long id, QuoteInfoForUpdateDTO infoForUpdate) {
        final var quote = findQuoteById(id);
        quote.updateQuote(infoForUpdate.getNewContent());
        quoteRepository.saveAndFlush(quote);
    }

    @Override
    @Transactional
    public void deleteQuote(Long id) {
        final var quote = findQuoteById(id);
        quoteRepository.delete(quote);
    }

    private Quote findQuoteById(Long id) {
        return quoteRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException(format("No quote with id = %d", id))
        );
    }
}
