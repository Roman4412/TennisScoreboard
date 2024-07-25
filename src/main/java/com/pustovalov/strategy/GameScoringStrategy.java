package com.pustovalov.strategy;

import com.pustovalov.enums.ScoreUnits;

public class GameScoringStrategy extends ScoringStrategy {
    private static final String DEFAULT_PTS = "0";
    private static final String FIFTEEN_PTS = "15";
    private static final String THIRTY_PTS = "30";
    private static final String FORTY_PTS = "40";
    private static final String ADVANTAGE = "AD";

    @Override
    public void count(Long playerId) {
        Long opponentId = score.getMatch().getOpponentId(playerId);
        String playerScore = score.getPoints(playerId, ScoreUnits.GAME);
        String opponentScore = score.getPoints(opponentId, ScoreUnits.GAME);

        switch(playerScore) {
            case DEFAULT_PTS -> score.setPoints(playerId, ScoreUnits.GAME, FIFTEEN_PTS);
            case FIFTEEN_PTS -> score.setPoints(playerId, ScoreUnits.GAME, THIRTY_PTS);
            case THIRTY_PTS -> score.setPoints(playerId, ScoreUnits.GAME, FORTY_PTS);

            case FORTY_PTS -> {
                if (opponentScore.equals(FORTY_PTS)) {
                    score.setPoints(playerId, ScoreUnits.GAME, ADVANTAGE);

                } else if (opponentScore.equals(ADVANTAGE)) {
                    score.setPoints(opponentId, ScoreUnits.GAME, FORTY_PTS);

                } else {
                    score.changeStrategy(new SetScoringStrategy(this.score));
                    score.getScoringStrategy().count(playerId);
                }
            }
            case ADVANTAGE -> {
                score.changeStrategy(new SetScoringStrategy(this.score));
                score.getScoringStrategy().count(playerId);
            }
        }
    }

    public GameScoringStrategy(Score score) {
        super(score);
    }

}
