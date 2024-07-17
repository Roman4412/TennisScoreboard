package com.pustovalov.state;

import com.pustovalov.entity.Score;
import com.pustovalov.enums.ScoreUnits;

public class SetScoring extends MatchScoringState {

    @Override
    public void count(Long playerId) {
        Long opponentId = score.getOpponentId(playerId);
        int playerScore = Integer.parseInt(score.getCurrentScore().get(playerId).get(ScoreUnits.GAME));
        int opponentScore = Integer.parseInt(score.getPlayerScore(opponentId, ScoreUnits.GAME));

        score.resetScore(ScoreUnits.POINT);
        score.setCurrentScore(playerId, ScoreUnits.GAME, String.valueOf(++playerScore));

        if (playerScore >= GAME_PTS_FOR_WIN && (playerScore - opponentScore >= POINT_DIFFERENCE_FOR_WIN)) {
            score.changeState(new MatchScoring(score));
            score.getMatchScoringState().count(playerId);

        } else if (playerScore == opponentScore && (playerScore == GAME_PTS_FOR_WIN)) {
            score.setMatchScoringState(new TiebreakScoring(this.score));

        } else if (playerScore == 7) {
            score.changeState(new MatchScoring(score));
            score.getMatchScoringState().count(playerId);

        } else {
            score.changeState(new GameScoring(score));
        }
    }

    public SetScoring(Score score) {
        super(score);
    }

}
