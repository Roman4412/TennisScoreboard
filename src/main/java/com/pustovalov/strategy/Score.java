package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Points;
import com.pustovalov.enums.ScoreUnits;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Score {

    private Map<Long, Points> currentScore;

    private Match match;

    private ScoringStrategy scoringStrategy;

    public void count(Long playerId) {
        scoringStrategy.count(playerId);
    }
    public String getPoints(Long playerId, ScoreUnits scoreUnit) {
        if (currentScore.containsKey(playerId)) {
            return currentScore.get(playerId).getPoint(scoreUnit);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    void setPoints(Long playerId, ScoreUnits scoreUnit, String value) {
        if (currentScore.containsKey(playerId)) {
            Points points = currentScore.get(playerId);
            points.setPoint(scoreUnit, value);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    void resetScore(ScoreUnits scoreUnit) {
        currentScore.get(match.getPlayerOne().getId()).setPoint(scoreUnit,Points.DEFAULT_VALUE);
        currentScore.get(match.getPlayerTwo().getId()).setPoint(scoreUnit,Points.DEFAULT_VALUE);
    }

    void changeStrategy(ScoringStrategy strategy) {
        this.scoringStrategy = strategy;
    }

    public void setMatch(Match match) {
        this.match = match;
        initScore();
    }

    private void initScore() {
        Long playerOneId = match.getPlayerOne().getId();
        Long playerTwoId = match.getPlayerTwo().getId();

        currentScore = new ConcurrentHashMap<>();
        currentScore.put(playerOneId, new Points());
        currentScore.put(playerTwoId, new Points());

        scoringStrategy = new GameScoringStrategy(this);
    }

}
