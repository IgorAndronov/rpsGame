package com.interview.task.dal.repository;

import com.interview.task.dal.entity.RpsResults;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RpsGameRepository extends CassandraRepository<RpsResults, RpsResults.RpsResultsKey> {

    List<RpsResults> findByKeyUserId(final String userId);
    List<RpsResults> findByKeyUserIdAndKeyDate(final String userId, final LocalDateTime date);

    @Query("select * from rps_results\n" +
            "where user_id = ?0\n" +
            "LIMIT ?1;")
    List<RpsResults> findByKeyUserIdLimitedTo(final String userId, int limit);
}
