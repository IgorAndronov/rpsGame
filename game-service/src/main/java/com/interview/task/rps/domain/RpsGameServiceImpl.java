package com.interview.task.rps.domain;

import com.interview.task.dal.entity.RpsResults;
import com.interview.task.dal.repository.RpsGameRepository;
import com.interview.task.rps.web.eh.UnknownGameMovement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.interview.task.rps.domain.GameConstants.*;
import static java.util.stream.Collectors.toList;


@Service
@Slf4j
public class RpsGameServiceImpl implements RpsGameService {

    public static final int SEARCH_SEQUENCE_LENGTH = 3;
    public static final int MIN_AMOUNT_OF_STEPS_TO_UNALIZE = 7;
    public static final int MAX_AMOUNT_OF_STEPS_TO_UNALIZE = 15;
    public static final double PROBABILITY_THRESHOLD = 0.4;



    @Autowired
    RpsGameRepository rpsGameRepository;

    @Override
    public Character getNextMove(String userId) {
        List<RpsResults> data = rpsGameRepository.findByKeyUserIdLimitedTo(userId, MAX_AMOUNT_OF_STEPS_TO_UNALIZE);

        return getStepValue(data.stream().map(item -> item.getUserCombination()).collect(toList()));
    }

    @Override
    public void saveGameResults(String userId, String userGameMovement, String serverGameMovement){
        Character userGameMovementCode = getMovementCode(userGameMovement);
        Character serverGameMovementCode = getMovementCode(serverGameMovement);

        RpsResults.RpsResultsKey rpsResultsKey = new RpsResults.RpsResultsKey(userId, LocalDateTime.now());
        RpsResults rpsResults = new RpsResults(rpsResultsKey, userGameMovementCode.toString(), serverGameMovementCode.toString(), getWinner(userGameMovementCode, serverGameMovementCode));

        rpsGameRepository.save(rpsResults);
    }

    private Character getMovementCode(String gameMovement) {
        Optional<Map.Entry<Character, String>> gameMovementData = GAME_DEFINITIONS.entrySet().stream()
                .filter(item -> item.getValue().equalsIgnoreCase(gameMovement)).findAny();

        if(!gameMovementData.isPresent()){
            log.error(gameMovement + " is not allowed game movement");
            throw new UnknownGameMovement(gameMovement + " is not allowed game movement");
        }

        return gameMovementData.get().getKey();
    }

    private String getWinner(Character userGameMovement, Character serverGameMovement) {

        if(userGameMovement.equals(serverGameMovement)){
            return DRAW;
        };
        if(serverGameMovement.equals(WIN_COMBINATIONS.get(userGameMovement))){
            return SERVER_WON;
        };

        return USER_WON;
    }


    private Character getStepValue(List<String> data){

        if(data.size()> MIN_AMOUNT_OF_STEPS_TO_UNALIZE){
            String dataString = String.join("", data);

            for(int currentSearchSequenceLength = 1; currentSearchSequenceLength<= SEARCH_SEQUENCE_LENGTH; currentSearchSequenceLength++){
                Character result = tryToGuessWithProbability(dataString,currentSearchSequenceLength);
                if(!result.equals(NOT_FOUND)){
                    return result;
                }
            }

            return applySimpleStatisticStrategy(data);
        }

        return applyRandomStrategy();
    }


    private Character tryToGuessWithProbability(String dataString, int currentSearchSequenceLength ){
        String searchCombination = dataString.substring(dataString.length()- currentSearchSequenceLength);
        Map<Character,Integer> nextMoveFrequency = getNextMoveFrequency(searchCombination, dataString);
        if(nextMoveFrequency.size()>0){
            Optional<Map.Entry<Character,Integer>> maxVal = nextMoveFrequency.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
            int total =  nextMoveFrequency.entrySet().stream().map(item -> item.getValue()).reduce(Integer::sum).get();
            double probability =(double) maxVal.get().getValue()/total;
            if(probability> PROBABILITY_THRESHOLD){
                return WIN_COMBINATIONS.get(maxVal.get().getKey());
            }
        }
        return NOT_FOUND;
    }

    private Character applySimpleStatisticStrategy(List<String> data) {
        Map<Character, Integer> result = new HashMap<>();
        data.stream().forEach(item -> {
            Character character = item.charAt(0);
            if(result.get(character)==null){
                result.put(character, 1);
            }else{
                result.put(character, result.get(character)+1);
            }
        });

        return result.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    private Character applyRandomStrategy() {
        Double randomIndex = Math.random()*WIN_COMBINATIONS.size();
        return  (Character) (WIN_COMBINATIONS.keySet().toArray())[randomIndex.intValue()];

    }

    Map<Character, Integer> getNextMoveFrequency(String substring, String sourceString){
        Map<Character, Integer> result = new HashMap<>();

        int idx = 0;
        int substringLength = substring.length();
        int sourceStringLength = sourceString.length();
        while ((idx = sourceString.indexOf(substring, idx)) != -1){
            idx+=substringLength;
            if(idx<sourceStringLength){
                Character character = sourceString.charAt(idx);
                if(result.get(character)==null){
                    result.put(character, 1);
                }else{
                    result.put(character, result.get(character)+1);
                }
            }
        }

        return result;
    }


}
