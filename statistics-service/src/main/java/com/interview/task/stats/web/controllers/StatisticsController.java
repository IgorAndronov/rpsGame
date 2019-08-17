package com.interview.task.stats.web.controllers;

import com.interview.task.dal.entity.RpsResults;
import com.interview.task.dal.repository.RpsGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/games/rps")
@Slf4j
public class StatisticsController {

    @Autowired
    RpsGameRepository rpsGameRepository;


    @GetMapping("/users/{userId}/limits/{limit}")
    public List<RpsResults> allProducts(@PathVariable String userId, @PathVariable int limit ) {
        log.info("Get request received: userID= "+userId + " limit= "+limit);
        return rpsGameRepository.findByKeyUserIdLimitedTo(userId, limit);
    }

}
