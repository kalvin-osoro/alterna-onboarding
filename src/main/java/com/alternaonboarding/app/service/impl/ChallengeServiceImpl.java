package com.alternaonboarding.app.service.impl;

import com.alternaonboarding.app.models.Challenge;
import com.alternaonboarding.app.repository.ChallengeRepository;
import com.alternaonboarding.app.service.ChallengeService;

import java.util.List;

public class ChallengeServiceImpl implements ChallengeService {

    private static ChallengeRepository challengeRepository;
    @Override
    public void saveChallenge(Challenge challenge) {
        challengeRepository.save(challenge);

    }

    @Override
    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    @Override
    public List<Challenge> getChallengesForWeekRange(int startWeek, int endWeek) {
        return challengeRepository.findByWeekNumberBetween(startWeek, endWeek);
    }

    @Override
    public double getTotalSavings() {
        List<Challenge> challenges = getAllChallenges();
        return challenges.stream().mapToDouble(Challenge::getAmountSaved).sum();
    }
}
