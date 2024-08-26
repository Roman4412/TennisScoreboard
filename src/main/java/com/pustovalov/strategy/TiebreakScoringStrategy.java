package com.pustovalov.strategy;

import com.pustovalov.enums.ScoreUnits;

public class TiebreakScoringStrategy extends ScoringStrategy {
  private static final int ROUNDS_TO_WIN = 7;
  private static final int TIEBREAK_WIN_MARGIN = 2;

  public TiebreakScoringStrategy(Score score) {
    super(score);
  }

  @Override
  public void count(Long playerId) {
    Long opponentId = score.getMatch().getOpponentId(playerId);
    int playerScore = Integer.parseInt(score.getPoints(playerId, ScoreUnits.TIEBREAK));
    int opponentScore = Integer.parseInt(score.getPoints(opponentId, ScoreUnits.TIEBREAK));

    score.setPoints(playerId, ScoreUnits.TIEBREAK, String.valueOf(++playerScore));

    if (playerScore >= ROUNDS_TO_WIN && (playerScore - opponentScore >= TIEBREAK_WIN_MARGIN)) {

      score.resetScore(ScoreUnits.TIEBREAK);
      score.changeStrategy(new SetScoringStrategy(score));
      score.getScoringStrategy().count(playerId);
    }
  }
}
