package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.Quote;
import com.example.kameleoontechnicaltask.repository.query.QuoteInfoWithUsersLastVote;
import com.example.kameleoontechnicaltask.repository.query.QuoteScoreGroupByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query(
        value = "select quote.id as id," +
            "          sum(case when (vote.type = 'UPVOTE' or vote.type = 'DOWNVOTE_TO_NO_VOTE') then 1 " +
            "                   when (vote.type = 'DOWNVOTE' or vote.type = 'UPVOTE_TO_NO_VOTE') then -1 " +
            "                   when (vote.type = 'DOWNVOTE_TO_UPVOTE') then 2 " +
            "                   when (vote.type = 'UPVOTE_TO_DOWNVOTE') then -2 else 0 end) as score, " +
            "          cast(vote.date_of_voting as date) as date " +
            "from vote " +
            "join quote on vote.quote_id = quote.id " +
            "where quote.id in :ids " +
            "group by quote.id, date " +
            "order by date",
        nativeQuery = true
    )
    List<QuoteScoreGroupByDate> findQuotesScoreByIds(@Param("ids") List<Long> ids);

    @Query(
        value = "select * " +
            "from (select quote.id                  as id, " +
            "             quote.content             as content, " +
            "             users.id                  as userWhoCreatedId, " +
            "             users.name                as userWhoCreatedName, " +
            "             users.email               as userWhoCreatedEmail, " +
            "             quote.date_of_creation    as dateOfCreation, " +
            "             quote.date_of_last_update as dateOfLastUpdateNullable, " +
            "             quote.score               as score, " +
            "             users_votes.type          as usersLastVoteNullable, " +
            "             row_number() over ( " +
            "                 partition by quote.id " +
            "                 order by users_votes.date_of_voting desc " +
            "             )                         as rn " +
            "      from quote " +
            "      join users on quote.user_who_created_id = users.id " +
            "      left join (select * " +
            "                 from vote " +
            "                 where vote.user_who_created_id = :user_id) users_votes " +
            "           on users_votes.quote_id = quote.id " +
            "      where quote.id in :ids) " +
            "where rn = 1 " +
            "   or usersLastVoteNullable is null",
        nativeQuery = true
    )
    List<QuoteInfoWithUsersLastVote> findQuotesInfoWithUsersLastVoteByIds(
        @Param("ids") List<Long> ids,
        @Param("user_id") Long userId
    );

    @Query(value = "select id from quote order by random() limit 1", nativeQuery = true)
    Optional<Long> findRandomQuoteId();

    @Query(value = "select id from quote order by score desc limit :lim", nativeQuery = true)
    List<Long> findTopQuoteIds(@Param("lim") Integer limit);

    @Query(value = "select id from quote order by score limit :lim", nativeQuery = true)
    List<Long> findFlopQuoteIds(@Param("lim") Integer limit);

    @Query(value = "select id from quote order by date_of_creation desc limit :lim", nativeQuery = true)
    List<Long> findLastQuoteIds(@Param("lim") Integer limit);
}
