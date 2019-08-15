package com.interview.task.rps.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RpsGameResults {
    private String userId;
    private String userGameMovement;
    private String serverGameMovement;

}
