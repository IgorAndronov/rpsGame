package com.interview.task.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@AllArgsConstructor
@Getter
@Setter
@Table("rps_results")
public class RpsResults {

    @PrimaryKey
    private RpsResultsKey key;

    @Column("user_combination")
    private String userCombination;

    @Column("server_combination")
    private String serverCombination;

    @Column("result")
    private String gameResult;


    @AllArgsConstructor
    @Getter
    @Setter
    @PrimaryKeyClass
    public static class RpsResultsKey implements Serializable {

        @PrimaryKeyColumn(name = "user_id", type = PARTITIONED)
        private String userId;

        @PrimaryKeyColumn(name = "date", ordinal = 0, ordering = DESCENDING)
        private LocalDateTime date;



    }

}

