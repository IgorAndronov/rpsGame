package com.interview.task.rps.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.interview.task")
@SpringBootApplication
public class RpsGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpsGameApplication.class, args);
    }

}
