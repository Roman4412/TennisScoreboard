package com.pustovalov.model.state;

import com.pustovalov.model.pojo.MatchScore;

public class Point extends MatchState {
    public Point(MatchScore matchScore) {
        super(matchScore);
    }

    @Override
    public void count(Long playerId) {
        Long opponentId = matchScore.getOpponentId(playerId);
        String playerScore = matchScore.getScore().get(playerId).get(POINT);
        String opponentScore = matchScore.getPlayersScore(opponentId, POINT);

        switch(playerScore) {
            case DEFAULT_PTS -> matchScore.setScore(playerId, POINT, FIFTEEN_PTS);
            case FIFTEEN_PTS -> matchScore.setScore(playerId, POINT, THIRTY_PTS);
            case THIRTY_PTS -> matchScore.setScore(playerId, POINT, FORTY_PTS);

            case FORTY_PTS -> {
                if (opponentScore.equals(FORTY_PTS)) {
                    matchScore.setScore(playerId, POINT, ADVANTAGE);

                } else if (opponentScore.equals(ADVANTAGE)) {
                    matchScore.setScore(opponentId, POINT, FORTY_PTS);

                } else {
                    matchScore.resetScore(POINT);

                    matchScore.changeState(new Game(this.matchScore));
                    matchScore.countPoint(playerId);
                }
            }
            case ADVANTAGE -> {
                matchScore.resetScore(POINT);
                matchScore.changeState(new Game(this.matchScore));
                matchScore.countPoint(playerId);
            }
        }
    }

}
