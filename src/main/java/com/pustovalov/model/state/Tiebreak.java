package com.pustovalov.model.state;

import com.pustovalov.model.pojo.MatchScore;

public class Tiebreak extends MatchState {
    @Override
    public void count(Long playerId) {
        Long opponentId = matchScore.getOpponentId(playerId);
        int playerScore = Integer.parseInt(matchScore.getScore().get(playerId).get(TIEBREAK));
        int opponentScore = Integer.parseInt(matchScore.getPlayersScore(opponentId, TIEBREAK));

        matchScore.setScore(playerId, TIEBREAK, String.valueOf(++playerScore));

        if (playerScore >= TIEBREAK_PTS_FOR_WIN &&
            (playerScore - opponentScore >= POINT_DIFFERENCE_FOR_WIN)) {

            matchScore.resetScore(TIEBREAK);
            matchScore.changeState(new Game(matchScore));
            matchScore.countPoint(playerId);
        }
    }

    public Tiebreak(MatchScore matchScore) {
        super(matchScore);
    }

}
