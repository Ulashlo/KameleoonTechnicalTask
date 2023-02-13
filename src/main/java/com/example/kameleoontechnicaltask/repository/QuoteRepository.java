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
            "          sum(case when (vote.type = 'UPVOTE') then 1 else -1 end) as score, " +
            "          cast(vote.date_of_voting as date) as date " +
            "from vote " +
            "join quote on vote.quote_id = quote.id " +
            "where quote.id in :ids " +
            "group by quote.id, date " +
            "order by date",
        nativeQuery = true
    )
    List<QuoteScoreGroupByDate> getQuotesScoreByIds(@Param("ids") List<Long> ids);

    @Query(
        value = "select quote.id as id," +
            "           quote.content as content," +
            "           users.id as userWhoCreatedId," +
            "           users.name as userWhoCreatedName," +
            "           users.email as userWhoCreatedEmail," +
            "           quote.date_of_creation as dateOfCreation," +
            "           quote.date_of_last_update as dateOfLastUpdateNullable," +
            "           quote.score as score," +
            "           vote.type as usersLastVoteNullable " +
            "from quote " +
            "left join vote on vote.quote_id = quote.id " +
            "join users on quote.user_who_created_id = users.id " +
            "where quote.id in :ids and " +
            "      vote.user_who_created_id = :user_id " +
            "order by vote.date_of_voting desc " +
            "limit 1",
        nativeQuery = true
    )
    List<QuoteInfoWithUsersLastVote> getQuotesInfoWithUsersLastVoteByIds(
        @Param("ids") List<Long> ids,
        @Param("user_id") Long userId
    );

    @Query(value = "select id from quote order by random() limit 1", nativeQuery = true)
    Optional<Long> getRandomQuoteId();

    @Query(value = "select id from quote order by score desc limit :lim", nativeQuery = true)
    List<Long> getTopQuoteIds(@Param("lim") Integer limit);

    @Query(value = "select id from quote order by score limit :lim", nativeQuery = true)
    List<Long> getFlopQuoteIds(@Param("lim") Integer limit);

    @Query(value = "select id from quote order by date_of_creation desc limit :lim", nativeQuery = true)
    List<Long> getLastQuoteIds(@Param("lim") Integer limit);
}
