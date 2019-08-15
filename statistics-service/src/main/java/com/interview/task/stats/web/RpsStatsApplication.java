package com.interview.task.stats.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.interview.task")
@SpringBootApplication
public class RpsStatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpsStatsApplication.class, args);
    }
}
