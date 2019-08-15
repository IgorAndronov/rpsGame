package com.interview.task.rps.domain;

public interface RpsGameService {

    Character getNextMove(String userId);
    void saveGameResults(String userId, String userGameMovement, String serverGameMovement);
}
