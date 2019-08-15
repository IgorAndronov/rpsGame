package com.interview.task.rps.web.eh;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownGameMovement extends RuntimeException {
    public UnknownGameMovement(String message) {
        super(message);
    }
}
