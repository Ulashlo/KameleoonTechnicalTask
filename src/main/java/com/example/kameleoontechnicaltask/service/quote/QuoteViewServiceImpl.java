package com.example.kameleoontechnicaltask.service.quote;

import com.example.kameleoontechnicaltask.controller.dto.quote.QuoteDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.RandomQuoteResponseDTO;
import com.example.kameleoontechnicaltask.controller.dto.quote.UsersVote;
import com.example.kameleoontechnicaltask.controller.dto.quote.VoteScoreDTO;
import com.example.kameleoontechnicaltask.controller.dto.user.UserLinkDTO;
import com.example.kameleoontechnicaltask.model.VoteType;
import com.example.kameleoontechnicaltask.repository.QuoteRepository;
import com.example.kameleoontechnicaltask.repository.query.QuoteInfoWithUsersLastVote;
import com.example.kameleoontechnicaltask.repository.query.QuoteScoreGroupByDate;
import com.example.kameleoontechnicaltask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteViewServiceImpl implements QuoteViewService {
    private final QuoteRepository quoteRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public RandomQuoteResponseDTO getRandomQuote() {
        final var randomQuoteId = quoteRepository.getRandomQuoteId();
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
    public List<QuoteDTO> getTopQuotes() {
        final var topQuoteIds = quoteRepository.getTopQuoteIds(10);
        return getQuoteDTOListByIds(topQuoteIds);
    }

    @Override
    public List<QuoteDTO> getFlopQuotes() {
        final var flopQuoteIds = quoteRepository.getFlopQuoteIds(10);
        return getQuoteDTOListByIds(flopQuoteIds);
    }

    @Override
    public List<QuoteDTO> getLastQuotes() {
        final var lastQuoteIds = quoteRepository.getLastQuoteIds(10);
        return getQuoteDTOListByIds(lastQuoteIds);
    }

    private List<QuoteDTO> getQuoteDTOListByIds(List<Long> ids) {
        final var currentUserId = userService.getCurrentUser().getId();
        final var quoteInfoWithUsersLastVotes = quoteRepository.getQuotesInfoWithUsersLastVoteByIds(
            ids,
            currentUserId
        );
        final var quoteScoreGroupByDates = quoteRepository.getQuotesScoreByIds(ids);
        final var quotesScoreMap = quoteScoreGroupByDates.stream()
            .collect(Collectors.groupingBy(QuoteScoreGroupByDate::getId));
        return quoteInfoWithUsersLastVotes.stream()
            .map(info -> {
                final var scores = quotesScoreMap.get(info.getId());
                return createQuoteDTO(info, scores);
            }).collect(Collectors.toList());
    }

    private QuoteDTO createQuoteDTO(QuoteInfoWithUsersLastVote quoteInfoWithUsersLastVotes,
                                    List<QuoteScoreGroupByDate> quoteScoreGroupByDates) {

        return new QuoteDTO(
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
            getUsersVote(
                quoteInfoWithUsersLastVotes.getUsersLastVote()
            ),
            createVoteScoreList(quoteScoreGroupByDates)
        );
    }

    private List<VoteScoreDTO> createVoteScoreList(List<QuoteScoreGroupByDate> quoteScoreGroupByDates) {
        final var voteScoreList = new ArrayList<VoteScoreDTO>();
        for (int i = 0; i < quoteScoreGroupByDates.size(); i++) {
            final var previousScore = i > 0 ? quoteScoreGroupByDates.get(i - 1).getScore() : 0;
            final var currentScore = quoteScoreGroupByDates.get(i).getScore() + previousScore;
            var currentDate = quoteScoreGroupByDates.get(i).getDate();
            voteScoreList.add(
                new VoteScoreDTO(
                    currentScore,
                    currentDate
                )
            );
            if (i == quoteScoreGroupByDates.size() - 1) {
                break;
            }
            final var nextDate = quoteScoreGroupByDates.get(i + 1).getDate();
            currentDate = currentDate.plusDays(1);
            while (currentDate.isBefore(nextDate)) {
                voteScoreList.add(
                    new VoteScoreDTO(
                        currentScore,
                        currentDate
                    )
                );
            }
        }
        return voteScoreList;
    }

    private UsersVote getUsersVote(Optional<VoteType> usersLastVote) {
        return usersLastVote.map(
            voteType -> switch (voteType) {
                case UPVOTE -> UsersVote.UPVOTE;
                case DOWNVOTE -> UsersVote.DOWNVOTE;
            }
        ).orElse(UsersVote.NO_VOTE);
    }
}
