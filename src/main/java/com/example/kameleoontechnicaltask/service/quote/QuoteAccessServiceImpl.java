package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service("QuoteAccessService")
@RequiredArgsConstructor
public class QuoteAccessServiceImpl implements QuoteAccessService {
    private final UserService userService;
    private final QuoteRepository quoteRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean hasAccessToEditOrDeleteQuote(Authentication authentication, Long quoteId) {
        final var currentUser = userService.getUserFromAuthentication(authentication);
        final var quote = quoteRepository.findById(quoteId).orElseThrow(
            () -> new NoSuchElementException(format("No quote with id = %d", quoteId))
        );
        return quote.getUserWhoCreated().getId().equals(currentUser.getId());
    }
}
