package com.alternaonboarding.app.repository;

import com.alternaonboarding.app.models.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByWeekNumberBetween(int startWeek, int endWeek);
}
