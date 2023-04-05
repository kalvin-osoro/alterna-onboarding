package com.alternaonboarding.app.service;

import com.alternaonboarding.app.models.Challenge;

import java.util.List;

public interface ChallengeService {
    void saveChallenge(Challenge challenge);

    List<Challenge> getAllChallenges();

    List<Challenge> getChallengesForWeekRange(int startWeek, int endWeek);

    double getTotalSavings();
}
