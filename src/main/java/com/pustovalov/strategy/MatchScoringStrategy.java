package com.pustovalov.strategy;


import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;

public class MatchScoringStrategy extends ScoringStrategy {

  public MatchScoringStrategy(Score score) {
    super(score);
  }

  @Override
  public void count(Long playerId) {
    int playerScore = Integer.parseInt(score.getPoint(playerId, PointUnits.MATCH).getValue());

    score.saveScore(PointUnits.SET);
    score.resetScore(PointUnits.SET);
    score.addPoint(playerId, String.valueOf(++playerScore), PointUnits.MATCH);

    if (playerScore == 2) {
      score.saveScore(PointUnits.MATCH);
      Match match = score.getMatch();
      match.setWinner(defineWinner(playerId));
      match.finish();
    } else {
      score.changeStrategy(new GameScoringStrategy(score));
    }
  }

  private Player defineWinner(Long playerId) {
    Match match = score.getMatch();
    Player playerOne = match.getPlayerOne();
    Player playerTwo = match.getPlayerTwo();

    return playerOne.getId().equals(playerId) ? playerOne : playerTwo;
  }

}
