package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;
import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final Log log = LogFactory.getLog(getClass());
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void createQuote(QuoteInfoForCreateDTO infoForCreate) {
        final var user = userService.getCurrentAuthenticatedUser();
        final var quote = quoteRepository.saveAndFlush(
            new Quote(
                infoForCreate.getContent(),
                user
            )
        );
        log.info(format("User %s create quote with id = %d", user.getName(), quote.getId()));
    }

    @Override
    @Transactional
    public void updateQuote(Long id, QuoteInfoForUpdateDTO infoForUpdate) {
        final var quote = findQuoteById(id);
        quote.updateQuote(infoForUpdate.getNewContent());
        quoteRepository.saveAndFlush(quote);
        log.info(format("Quote with id = %d was updated", quote.getId()));
    }

    @Override
    @Transactional
    public void deleteQuote(Long id) {
        final var quote = findQuoteById(id);
        quoteRepository.delete(quote);
        log.info(format("Quote with id = %d was deleted", quote.getId()));
    }

    private Quote findQuoteById(Long id) {
        return quoteRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException(format("No quote with id = %d", id))
        );
    }
}
