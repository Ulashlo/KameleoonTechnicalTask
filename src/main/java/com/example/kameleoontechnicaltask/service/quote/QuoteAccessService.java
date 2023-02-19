package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.model.Quote;
import org.springframework.security.core.Authentication;

import java.util.NoSuchElementException;

/**
 * Service provides functionality for checking if authenticated users has access for actions with quotes
 */
public interface QuoteAccessService {
    /**
     * Checks whether user has access to update or delete {@linkplain Quote}.
     *
     * @param authentication authentication object
     * @param quoteId id of quote
     * @return whether access is permitted
     * @throws NoSuchElementException if there are no quote with given id
     */
    boolean hasAccessToEditOrDeleteQuote(Authentication authentication, Long quoteId);
}
