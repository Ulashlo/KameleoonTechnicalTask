package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForCreateDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteInfoForUpdateDTO;

import java.util.NoSuchElementException;

/**
 * Service provides functionality for working with a quotes
 */
public interface QuoteService {
    /**
     * Create a new quote.
     *
     * @param infoForCreate quote's content
     */
    void createQuote(QuoteInfoForCreateDTO infoForCreate);

    /**
     * Update quote with given id.
     *
     * @param infoForUpdate new quote's content
     * @throws NoSuchElementException if there are no quote with given id
     */
    void updateQuote(Long id, QuoteInfoForUpdateDTO infoForUpdate);

    /**
     * Delete quote with given id.
     *
     * @param id quote's id
     * @throws NoSuchElementException if there are no quote with given id
     */
    void deleteQuote(Long id);
}
