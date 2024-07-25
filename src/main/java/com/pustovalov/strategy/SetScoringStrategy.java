package com.pustovalov.strategy;

import com.pustovalov.enums.ScoreUnits;

public class SetScoringStrategy extends ScoringStrategy {
    private static final int GAMES_TO_WIN = 6;
    private static final int SET_WIN_MARGIN = 2;
    @Override
    public void count(Long playerId) {
        Long opponentId = score.getMatch().getOpponentId(playerId);
        int playerScore = Integer.parseInt(score.getPoints(playerId, ScoreUnits.SET));
        int opponentScore = Integer.parseInt(score.getPoints(opponentId, ScoreUnits.SET));

        score.resetScore(ScoreUnits.GAME);
        score.setPoints(playerId, ScoreUnits.SET, String.valueOf(++playerScore));

        if (playerScore >= GAMES_TO_WIN && (playerScore - opponentScore >= SET_WIN_MARGIN)) {
            score.changeStrategy(new MatchScoringStrategy(score));
            score.getScoringStrategy().count(playerId);

        } else if (playerScore == opponentScore && (playerScore == GAMES_TO_WIN)) {
            score.changeStrategy(new TiebreakScoringStrategy(this.score));

        } else if (playerScore == 7) {
            score.changeStrategy(new MatchScoringStrategy(score));
            score.getScoringStrategy().count(playerId);

        } else {
            score.changeStrategy(new GameScoringStrategy(score));
        }
    }

    public SetScoringStrategy(Score score) {
        super(score);
    }

}
