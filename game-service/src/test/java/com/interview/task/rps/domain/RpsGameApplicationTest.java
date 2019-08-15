package com.interview.task.rps.domain;

import com.interview.task.dal.entity.RpsResults;
import com.interview.task.dal.repository.RpsGameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.interview.task.rps.domain.RpsGameServiceImpl.MAX_AMOUNT_OF_STEPS_TO_UNALIZE;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RpsGameServiceImpl.class})
public class RpsGameApplicationTest {

    @Autowired
    RpsGameService rpsGameService;

    @MockBean
    RpsGameRepository rpsGameRepository;

    @Before
    public void setUp() {
        List<String> data = Arrays.asList("p","s","c","p","s","c","s","s","c","p","s","s", "c", "p", "s", "c", "p", "p", "s", "c");
        String userId = "123";
        List<RpsResults> rpsResultsData = data.stream().map(item -> {
            RpsResults.RpsResultsKey key = new RpsResults.RpsResultsKey(userId, LocalDateTime.now());
            return new RpsResults(key, item, "", "");
        }).collect(Collectors.toList());


        doReturn(rpsResultsData).when(rpsGameRepository).findByKeyUserIdLimitedTo(userId, MAX_AMOUNT_OF_STEPS_TO_UNALIZE);

    }

    @Test
    public void getNextMoveTest(){
        Character result = rpsGameService.getNextMove("123");

        assert(result).equals('c');

    }


}
