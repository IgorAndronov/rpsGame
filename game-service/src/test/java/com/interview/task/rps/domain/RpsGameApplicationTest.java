package com.interview.task.rps.domain;

import com.interview.task.dal.entity.RpsResults;
import com.interview.task.dal.repository.RpsGameRepository;
import com.interview.task.rps.web.eh.UnknownGameMovement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.interview.task.rps.domain.RpsGameServiceImpl.MAX_AMOUNT_OF_STEPS_TO_UNALIZE;
import static org.mockito.Mockito.doReturn;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@SpringBootTest(classes = {RpsGameServiceImpl.class})
public class RpsGameApplicationTest {

    boolean init=false;

    @Autowired
    RpsGameService rpsGameService;

    @MockBean
    RpsGameRepository rpsGameRepository;


    @Before
    public void setUp() {

        List<String> data = Arrays.asList("p","s","c","p","s","c","s","s","c","p","s","s", "c", "p", "s", "c", "p", "p", "s", "c");
        String userId = "123";
        mockRepository(data, userId);

        data = Arrays.asList("p","s","c","p","p","c","p","c","c","p","s","c", "p", "p", "s", "p", "c", "c", "s", "p");
        userId = "12345";
        mockRepository(data, userId);

    }

    private void mockRepository(List<String> data, String userId) {
        List<RpsResults> rpsResultsData = data.stream().map(item -> {
            RpsResults.RpsResultsKey key = new RpsResults.RpsResultsKey(userId, LocalDateTime.now());
            return new RpsResults(key, item, "", "");
        }).collect(Collectors.toList());


        doReturn(rpsResultsData).when(rpsGameRepository).findByKeyUserIdLimitedTo(userId, MAX_AMOUNT_OF_STEPS_TO_UNALIZE);
    }

    @Test
    public void getNextMoveOneStepCombinationTest(){
        Character result = rpsGameService.getNextMove("123");
        Assert.assertEquals(new Character('c'), result);

    }

    @Test
    public void getNextMoveTwoStepCombinationTest(){
        Character result = rpsGameService.getNextMove("12345");
        Assert.assertEquals(new Character('s'), result);

    }

    @Test(expected = UnknownGameMovement.class)
    public void saveResultsTest(){
        rpsGameService.saveGameResults("123", "stone", "dfg");

    }

    @Test
    public void getWinnerTest() throws Exception{
        String result = Whitebox.invokeMethod(rpsGameService,
                    "getWinner", 's','p');
        Assert.assertEquals("S",result);

    }

    @Test
    public void getMovementCode() throws Exception{
        Character result = Whitebox.invokeMethod(rpsGameService,
                "getMovementCode", "stone");
        Assert.assertEquals(new Character('s'),result);

        result = Whitebox.invokeMethod(rpsGameService,
                "getMovementCode", "scissors");
        Assert.assertEquals(new Character('c'), result);

        result = Whitebox.invokeMethod(rpsGameService,
                "getMovementCode", "paper");
        Assert.assertEquals(new Character('p'), result);

    }

    @Test
    public void applySimpleStatisticStrategyTest() throws Exception{
        List<String> data = Arrays.asList("p","s","c","p","p","c","p","c","c","p","s","c", "p", "p", "s", "p", "c", "c", "s", "p");
        Character result = Whitebox.invokeMethod(rpsGameService,"applySimpleStatisticStrategy", data);
        Assert.assertEquals(new Character('p'),result);

    }

    @Test
    public void applyRandomStrategyTest() throws Exception{
        int amountOfIterations = 10000;

        Map<Character, Integer> frequency = new HashMap<>();
        for(int i=0; i<amountOfIterations; i++){
            Character result = Whitebox.invokeMethod(rpsGameService,"applyRandomStrategy");
            if(!frequency.containsKey(result)){
                frequency.put(result,1);
            }else{
                frequency.put(result, frequency.get(result)+1);
            }
        }

        frequency.values().stream().forEach(value -> {
            Assert.assertTrue(((double) value/amountOfIterations)>0.3);
        } );

    }


}
