package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.model.Quote;

import java.util.List;

/**
 * Service provide access to {@linkplain Quote}
 */
public interface QuoteViewService {
    /**
     * Find and return a random quote.
     *
     * @return a random {@linkplain Quote}
     */
    RandomQuoteResponseDTO getRandomQuote();

    /**
     * Find and return top quotes.
     *
     * @return list of top {@linkplain Quote}
     */
    List<QuoteDTO> getTopQuotes();

    /**
     * Find and return flop quotes.
     *
     * @return list of flop {@linkplain Quote}
     */
    List<QuoteDTO> getFlopQuotes();

    /**
     * Find and return last quotes.
     *
     * @return list of last {@linkplain Quote}
     */
    List<QuoteDTO> getLastQuotes();
}
