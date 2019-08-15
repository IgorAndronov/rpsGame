package com.interview.task.dal.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("users")
public class Users {

    @PrimaryKey("user_id")
    private String userId;
    @Column("user_name")
    private String userName;
}
