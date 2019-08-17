package com.interview.task.rps.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GeneralResponse {
    private  String status;
    private String message;
}
