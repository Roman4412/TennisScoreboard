package com.pustovalov.model.state;

import com.pustovalov.model.pojo.MatchScore;

public class Set extends MatchState {

    @Override
    public void count(Long playerId) {
        int playerScore = Integer.parseInt(matchScore.getPlayersScore(playerId, SET));

        matchScore.setScore(playerId, SET, String.valueOf(++playerScore));
        if (playerScore == 2) {
            //TODO notification of the end of the match in the match persistence service
            throw new RuntimeException("MATCH FINISHED");
        }
        matchScore.changeState(new Point(this.matchScore));
    }

    public Set(MatchScore matchScore) {
        super(matchScore);
    }

}
