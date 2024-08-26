package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.ScoreContainer;
import com.pustovalov.enums.ScoreUnits;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public class Score {

  private static final String ZERO = "0";

  private Map<Long, ScoreContainer> currentScore;

  private Map<Long, ScoreContainer> matchResults;

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

  public List<String> getResultPoints(Long playerId, ScoreUnits units) {
    if (currentScore.containsKey(playerId)) {
      return matchResults.get(playerId).getAllPoints(units);
    } else {
      throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
    }
  }

  void setPoints(Long playerId, ScoreUnits scoreUnit, String value) {
    if (currentScore.containsKey(playerId)) {
      ScoreContainer scoreContainer = currentScore.get(playerId);
      scoreContainer.addPoint(scoreUnit, value);
    } else {
      throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
    }
  }

  void resetScore(ScoreUnits scoreUnit) {
    currentScore.get(match.getPlayerOne().getId()).addPoint(scoreUnit, ZERO);
    currentScore.get(match.getPlayerTwo().getId()).addPoint(scoreUnit, ZERO);
  }

  void saveScore(ScoreUnits units) {
    Long playerOneId = match.getPlayerOne().getId();
    Long playerTwoId = match.getPlayerTwo().getId();

    String playerOnePts = getPoints(playerOneId, units);
    String playerTwoPts = getPoints(playerTwoId, units);

    matchResults.get(playerOneId).addPoint(units, playerOnePts);
    matchResults.get(playerTwoId).addPoint(units, playerTwoPts);
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
    currentScore.put(playerOneId, new ScoreContainer(ZERO));
    currentScore.put(playerTwoId, new ScoreContainer(ZERO));

    matchResults = new ConcurrentHashMap<>();
    matchResults.put(playerOneId, new ScoreContainer());
    matchResults.put(playerTwoId, new ScoreContainer());
    scoringStrategy = new GameScoringStrategy(this);
  }
}
