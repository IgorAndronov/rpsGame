package com.interview.task.rps.web.controllers;

import com.interview.task.rps.domain.RpsGameService;
import com.interview.task.rps.web.models.RpsGameMovementResponse;
import com.interview.task.rps.web.models.RpsGameResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


import static com.interview.task.rps.domain.GameConstants.GAME_DEFINITIONS;

@RestController
@RequestMapping("/api/rps")
@Slf4j
public class RpsGameController {

    @Autowired
    RpsGameService rpsGameService;

    @GetMapping("/users/{userId}")
    public RpsGameMovementResponse getNextMovement(@PathVariable String userId) {
        log.info("Get request received: method=getNextMovement, userID= "+userId);
        Character result = rpsGameService.getNextMove(userId);

        return new RpsGameMovementResponse(GAME_DEFINITIONS.get(result), LocalDateTime.now());
    }

    @PostMapping("/results")
    public void saveResults(@RequestBody RpsGameResults rpsGameResults){
        log.info("Post request received: method=saveResults, userId= "+rpsGameResults.getUserId());
        rpsGameService.saveGameResults(rpsGameResults.getUserId(), rpsGameResults.getUserGameMovement(), rpsGameResults.getServerGameMovement());
    }

}
