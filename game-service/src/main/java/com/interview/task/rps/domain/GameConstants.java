package com.interview.task.rps.domain;

import java.util.HashMap;
import java.util.Map;

public class GameConstants {
    public static final Character STONE= 's';
    public static final Character SCISSORS= 'c';
    public static final Character PAPER= 'p';

    public static final char NOT_FOUND = 'N';

    public static final String DRAW = "E";
    public static final String USER_WON = "U";
    public static final String SERVER_WON = "S";

    public static final Map<Character,Character> WIN_COMBINATIONS = new HashMap<Character,Character>(){{
        put(STONE, PAPER);
        put(PAPER, SCISSORS);
        put(SCISSORS, STONE);
    }};

    public static final Map<Character,String> GAME_DEFINITIONS = new HashMap<Character,String>(){{
        put(STONE, "Stone");
        put(PAPER, "Paper");
        put(SCISSORS, "Scissors");
    }};
}
