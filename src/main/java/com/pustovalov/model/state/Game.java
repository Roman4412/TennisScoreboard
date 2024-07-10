package com.pustovalov.model.state;

import com.pustovalov.model.pojo.MatchScore;

public class Game extends MatchState {

    @Override
    public void count(Long playerId) {
        Long opponentId = matchScore.getOpponentId(playerId);
        int playerScore = Integer.parseInt(matchScore.getScore().get(playerId).get(GAME));
        int opponentScore = Integer.parseInt(matchScore.getPlayersScore(opponentId, GAME));

        matchScore.setScore(playerId, GAME, String.valueOf(++playerScore));

        if (playerScore >= GAME_PTS_FOR_WIN &&
            (playerScore - opponentScore >= POINT_DIFFERENCE_FOR_WIN)) {

            matchScore.resetScore(GAME);
            matchScore.changeState(new Set(matchScore));
            matchScore.countPoint(playerId);

        } else if (playerScore == opponentScore && (playerScore == GAME_PTS_FOR_WIN)) {
            matchScore.setMatchState(new Tiebreak(this.matchScore));

        } else if (playerScore == 7) {
            matchScore.resetScore(GAME);
            matchScore.changeState(new Set(matchScore));
            matchScore.countPoint(playerId);

        } else {
            matchScore.changeState(new Point(matchScore));
        }
    }

    public Game(MatchScore matchScore) {
        super(matchScore);
    }

}
