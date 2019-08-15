package com.interview.task.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class UiProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiProxyApplication.class, args);
    }
}
