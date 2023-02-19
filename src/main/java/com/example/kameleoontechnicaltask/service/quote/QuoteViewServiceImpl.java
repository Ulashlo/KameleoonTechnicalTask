package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteWithScoreDynamicDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteScoreDTO;
import com.example.kameleoontechnicaltask.controller.dto.user.UserLinkDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.query.QuoteInfoWithUsersLastVote;
import com.example.kameleoontechnicaltask.repository.query.QuoteScoreGroupByDate;
import com.example.kameleoontechnicaltask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteViewServiceImpl implements QuoteViewService {
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public RandomQuoteResponseDTO getRandomQuote() {
        final var randomQuoteId = quoteRepository.findRandomQuoteId();
        if (randomQuoteId.isEmpty()) {
            return RandomQuoteResponseDTO.createEmptyResponse();
        }
        return RandomQuoteResponseDTO.createResponse(
            getQuoteDTOListByIds(
                List.of(
                    randomQuoteId.orElseThrow()
                )
            ).get(0)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteWithScoreDynamicDTO> getTopQuotes(Integer limit) {
        final var topQuoteIds = quoteRepository.findTopQuoteIds(limit);
        return getQuoteDTOListByIds(topQuoteIds).stream()
            .sorted(Comparator.comparing(QuoteWithScoreDynamicDTO::getScore).reversed())
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteWithScoreDynamicDTO> getFlopQuotes(Integer limit) {
        final var flopQuoteIds = quoteRepository.findFlopQuoteIds(limit);
        return getQuoteDTOListByIds(flopQuoteIds).stream()
            .sorted(Comparator.comparing(QuoteWithScoreDynamicDTO::getScore))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteWithScoreDynamicDTO> getLastQuotes(Integer pageNum, Integer pageSize) {
        final var lastQuoteIds = quoteRepository.findQuoteIdsWithPagination(
            PageRequest.of(
                pageNum,
                pageSize,
                Sort.by("dateOfCreation").descending()
            )
        );
        return getQuoteDTOListByIds(lastQuoteIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteWithScoreDynamicDTO> getMyQuotes() {
        final var currentUser = userService.getCurrentAuthenticatedUser();
        final var myQuoteIds = quoteRepository.findUsersQuoteIds(currentUser);
        return getQuoteDTOListByIds(myQuoteIds).stream()
            .sorted(Comparator.comparing(QuoteWithScoreDynamicDTO::getDateOfCreation).reversed())
            .toList();
    }

    private List<QuoteWithScoreDynamicDTO> getQuoteDTOListByIds(List<Long> ids) {
        final var currentUserId = userService.getCurrentUser().map(UserEntity::getId).orElse(null);
        final var quoteInfoWithUsersLastVotes = quoteRepository.findQuotesInfoWithUsersLastVoteByIds(
            ids,
            currentUserId
        );
        final var quoteScoreGroupByDates = quoteRepository.findQuotesScoreByIds(ids);
        final var quotesScoreMap = quoteScoreGroupByDates.stream()
            .collect(Collectors.groupingBy(QuoteScoreGroupByDate::getId));
        return quoteInfoWithUsersLastVotes.stream()
            .map(info -> {
                final var scores = quotesScoreMap.getOrDefault(info.getId(), List.of()).stream()
                    .sorted(Comparator.comparing(QuoteScoreGroupByDate::getDate))
                    .toList();
                return createQuoteDTO(info, scores);
            }).collect(Collectors.toList());
    }

    private QuoteWithScoreDynamicDTO createQuoteDTO(QuoteInfoWithUsersLastVote quoteInfoWithUsersLastVotes,
                                                    List<QuoteScoreGroupByDate> quoteScoreGroupByDates) {
        return new QuoteWithScoreDynamicDTO(
            quoteInfoWithUsersLastVotes.getId(),
            quoteInfoWithUsersLastVotes.getContent(),
            new UserLinkDTO(
                quoteInfoWithUsersLastVotes.getUserWhoCreatedId(),
                quoteInfoWithUsersLastVotes.getUserWhoCreatedName(),
                quoteInfoWithUsersLastVotes.getUserWhoCreatedEmail()
            ),
            quoteInfoWithUsersLastVotes.getDateOfCreation(),
            quoteInfoWithUsersLastVotes.getDateOfLastUpdate().orElse(null),
            quoteInfoWithUsersLastVotes.getScore(),
            quoteInfoWithUsersLastVotes.getUsersLastVoteType(),
            createVoteScoreList(quoteScoreGroupByDates)
        );
    }

    private List<VoteScoreDTO> createVoteScoreList(List<QuoteScoreGroupByDate> quoteScoreGroupByDates) {
        final var voteScoreList = new ArrayList<VoteScoreDTO>();
        for (int i = 0; i < quoteScoreGroupByDates.size(); i++) {
            final var previousScore = i > 0 ? voteScoreList.get(voteScoreList.size() - 1).getScore() : 0;
            final var currentScore = quoteScoreGroupByDates.get(i).getScore() + previousScore;
            var currentDate = quoteScoreGroupByDates.get(i).getDate();
            voteScoreList.add(
                new VoteScoreDTO(
                    currentScore,
                    currentDate
                )
            );
            final var nextDate =
                i < quoteScoreGroupByDates.size() - 1 ?
                    quoteScoreGroupByDates.get(i + 1).getDate() :
                    LocalDate.now().plusDays(1);
            currentDate = currentDate.plusDays(1);
            while (currentDate.isBefore(nextDate)) {
                voteScoreList.add(
                    new VoteScoreDTO(
                        currentScore,
                        currentDate
                    )
                );
                currentDate = currentDate.plusDays(1);
            }
        }
        return voteScoreList;
    }
}
