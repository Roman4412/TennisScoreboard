package com.pustovalov.state;

import com.pustovalov.entity.Score;
import com.pustovalov.enums.ScoreUnits;

public class GameScoring extends MatchScoringState {
    @Override
    public void count(Long playerId) {
        Long opponentId = score.getOpponentId(playerId);
        String playerScore = score.getCurrentScore().get(playerId).get(ScoreUnits.POINT);
        String opponentScore = score.getPlayerScore(opponentId, ScoreUnits.POINT);

        switch(playerScore) {
            case DEFAULT_PTS -> score.setCurrentScore(playerId, ScoreUnits.POINT, FIFTEEN_PTS);
            case FIFTEEN_PTS -> score.setCurrentScore(playerId, ScoreUnits.POINT, THIRTY_PTS);
            case THIRTY_PTS -> score.setCurrentScore(playerId, ScoreUnits.POINT, FORTY_PTS);

            case FORTY_PTS -> {
                if (opponentScore.equals(FORTY_PTS)) {
                    score.setCurrentScore(playerId, ScoreUnits.POINT, ADVANTAGE);

                } else if (opponentScore.equals(ADVANTAGE)) {
                    score.setCurrentScore(opponentId, ScoreUnits.POINT, FORTY_PTS);

                } else {
                    score.changeState(new SetScoring(this.score));
                    score.getMatchScoringState().count(playerId);
                }
            }
            case ADVANTAGE -> {
                score.changeState(new SetScoring(this.score));
                score.getMatchScoringState().count(playerId);
            }
        }
    }

    public GameScoring(Score score) {
        super(score);
    }

}
