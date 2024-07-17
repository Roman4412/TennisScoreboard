package com.pustovalov.state;

import com.pustovalov.entity.Score;
import com.pustovalov.enums.ScoreUnits;

public class TiebreakScoring extends MatchScoringState {
    @Override
    public void count(Long playerId) {
        Long opponentId = score.getOpponentId(playerId);
        int playerScore = Integer.parseInt(score.getCurrentScore().get(playerId).get(ScoreUnits.TIEBREAK));
        int opponentScore = Integer.parseInt(score.getPlayerScore(opponentId, ScoreUnits.TIEBREAK));

        score.setCurrentScore(playerId, ScoreUnits.TIEBREAK, String.valueOf(++playerScore));

        if (playerScore >= TIEBREAK_PTS_FOR_WIN &&
            (playerScore - opponentScore >= POINT_DIFFERENCE_FOR_WIN)) {

            score.resetScore(ScoreUnits.TIEBREAK);
            score.changeState(new SetScoring(score));
            score.getMatchScoringState().count(playerId);
        }
    }

    public TiebreakScoring(Score score) {
        super(score);
    }

}
