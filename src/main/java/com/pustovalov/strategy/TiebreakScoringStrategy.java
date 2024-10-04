package com.pustovalov.strategy;

import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;

public class TiebreakScoringStrategy extends ScoringStrategy {

    private static final int ROUNDS_TO_WIN = 7;

    private static final int TIEBREAK_WIN_MARGIN = 2;

    public TiebreakScoringStrategy(Score score) {
        super(score);
    }

    @Override
    public void count(Long playerId) {
        Long opponentId = score.getMatch().getOpponentId(playerId);
        int playerScore = Integer.parseInt(score.getPoint(playerId, PointUnits.TIEBREAK).getValue());
        int opponentScore = Integer.parseInt(score.getPoint(opponentId, PointUnits.TIEBREAK).getValue());

        score.addPoint(playerId, String.valueOf(++playerScore), PointUnits.TIEBREAK);

        if (playerScore >= ROUNDS_TO_WIN && (playerScore - opponentScore >= TIEBREAK_WIN_MARGIN)) {
            score.resetScore(PointUnits.TIEBREAK);
            score.changeStrategy(new SetScoringStrategy(score));
            score.getScoringStrategy().count(playerId);
        }
    }
}
