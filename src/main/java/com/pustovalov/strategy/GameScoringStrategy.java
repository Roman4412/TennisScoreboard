package com.pustovalov.strategy;

import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;

public class GameScoringStrategy extends ScoringStrategy {

    private static final String ZERO_PTS = "0";

    private static final String FIFTEEN_PTS = "15";

    private static final String THIRTY_PTS = "30";

    private static final String FORTY_PTS = "40";

    private static final String ADVANTAGE = "AD";

    public GameScoringStrategy(Score score) {
        super(score);
    }

    public void count(Long playerId) {
        Long opponentId = score.getMatch().getOpponentId(playerId);
        String playerScore = score.getPoint(playerId, PointUnits.GAME).getValue();
        String opponentScore = score.getPoint(opponentId, PointUnits.GAME).getValue();

        switch (playerScore) {
            case ZERO_PTS -> score.addPoint(playerId, FIFTEEN_PTS, PointUnits.GAME);
            case FIFTEEN_PTS -> score.addPoint(playerId, THIRTY_PTS, PointUnits.GAME);
            case THIRTY_PTS -> score.addPoint(playerId, FORTY_PTS, PointUnits.GAME);

            case FORTY_PTS -> {
                if (opponentScore.equals(FORTY_PTS)) {
                    score.addPoint(playerId, ADVANTAGE, PointUnits.GAME);

                } else if (opponentScore.equals(ADVANTAGE)) {
                    score.addPoint(opponentId, FORTY_PTS, PointUnits.GAME);

                } else {
                    score.changeStrategy(new SetScoringStrategy(score));
                    score.getScoringStrategy().count(playerId);
                }
            }
            case ADVANTAGE -> {
                score.changeStrategy(new SetScoringStrategy(score));
                score.getScoringStrategy().count(playerId);
            }
        }
    }
}
