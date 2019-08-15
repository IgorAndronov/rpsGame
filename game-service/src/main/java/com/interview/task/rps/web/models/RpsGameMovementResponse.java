package com.interview.task.rps.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class RpsGameMovementResponse {
    private String value;
    private LocalDateTime serverTime;
}
