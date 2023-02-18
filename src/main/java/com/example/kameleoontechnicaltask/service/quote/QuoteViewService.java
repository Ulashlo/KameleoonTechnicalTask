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
     * Find and return a random quote, or empty if there are no quotes yet.
     *
     * @return a random {@linkplain Quote}
     */
    RandomQuoteResponseDTO getRandomQuote();

    /**
     * Find and return top quotes.
     *
     * @return list of top {@linkplain Quote}
     */
    List<QuoteDTO> getTopQuotes(Integer limit);

    /**
     * Find and return flop quotes.
     *
     * @return list of flop {@linkplain Quote}
     */
    List<QuoteDTO> getFlopQuotes(Integer limit);

    /**
     * Find and return last quotes.
     *
     * @return list of last {@linkplain Quote}
     */
    List<QuoteDTO> getLastQuotes(Integer limit);
}
