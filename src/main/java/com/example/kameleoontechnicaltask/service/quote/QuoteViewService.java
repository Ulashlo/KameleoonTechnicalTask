package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteWithScoreDynamicDTO;
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
     * @return a random quote info
     */
    RandomQuoteResponseDTO getRandomQuote();

    /**
     * Find and return top quotes.
     *
     * @return list of top {@linkplain QuoteWithScoreDynamicDTO}
     */
    List<QuoteWithScoreDynamicDTO> getTopQuotes(Integer limit);

    /**
     * Find and return flop quotes.
     *
     * @return list of flop {@linkplain QuoteWithScoreDynamicDTO}
     */
    List<QuoteWithScoreDynamicDTO> getFlopQuotes(Integer limit);

    /**
     * Find and return last quotes.
     *
     * @return list of last {@linkplain QuoteWithScoreDynamicDTO}
     */
    List<QuoteWithScoreDynamicDTO> getLastQuotes(Integer pageNum, Integer pageSize);

    /**
     * Find and return current user's quotes.
     *
     * @return list of user's {@linkplain QuoteWithScoreDynamicDTO}
     */
    List<QuoteWithScoreDynamicDTO> getMyQuotes();
}
