package com.pustovalov.strategy;

import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;

public class SetScoringStrategy extends ScoringStrategy {
  private static final int GAMES_TO_WIN = 6;

  private static final int SET_WIN_MARGIN = 2;

  public SetScoringStrategy(Score score) {
    super(score);
  }

  @Override
  public void count(Long playerId) {
    Long opponentId = score.getMatch().getOpponentId(playerId);
    int playerScore = Integer.parseInt(score.getPoint(playerId, PointUnits.SET).getValue());
    int opponentScore = Integer.parseInt(score.getPoint(opponentId, PointUnits.SET).getValue());

    score.resetScore(PointUnits.GAME);
    score.addPoint(playerId, String.valueOf(++playerScore), PointUnits.SET);

    if (playerScore >= GAMES_TO_WIN && (playerScore - opponentScore >= SET_WIN_MARGIN)) {
      score.changeStrategy(new MatchScoringStrategy(score));
      score.getScoringStrategy().count(playerId);

    } else if (playerScore == opponentScore && (playerScore == GAMES_TO_WIN)) {
      score.changeStrategy(new TiebreakScoringStrategy(score));

    } else if (playerScore == 7) {
      score.changeStrategy(new MatchScoringStrategy(score));
      score.getScoringStrategy().count(playerId);

    } else {
      score.changeStrategy(new GameScoringStrategy(score));
    }
  }
}
