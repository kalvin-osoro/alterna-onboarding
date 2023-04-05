package com.alternaonboarding.app.controller;

import com.alternaonboarding.app.models.Challenge;
import com.alternaonboarding.app.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private static ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<?> saveChallenge(@RequestBody Challenge challenge) {
        challengeService.saveChallenge(challenge);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Challenge>> getAllChallenges() {
        List<Challenge> challenges = challengeService.getAllChallenges();
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
    @GetMapping("/week/{startWeek}/{endWeek}")
    public ResponseEntity<List<Challenge>> getChallengesForWeekRange(
            @PathVariable int startWeek,
            @PathVariable int endWeek
    ) {
        List<Challenge> challenges = challengeService.getChallengesForWeekRange(startWeek, endWeek);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
    @GetMapping("/total-savings")
    public ResponseEntity<Double> getTotalSavings() {
        double totalSavings = challengeService.getTotalSavings();
        return new ResponseEntity<>(totalSavings, HttpStatus.OK);
    }
}
