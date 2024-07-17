package com.pustovalov.state;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Score;
import com.pustovalov.enums.ScoreUnits;

public class MatchScoring extends MatchScoringState {

    @Override
    public void count(Long playerId) {
        int playerScore = Integer.parseInt(score.getPlayerScore(playerId, ScoreUnits.SET));
        Match match = score.getMatch();

        score.setCurrentScore(playerId, ScoreUnits.SET, String.valueOf(++playerScore));
        score.saveScore();
        score.resetScore(ScoreUnits.GAME);

        if (playerScore == 2) {

            defineWinner(playerId, match);
            match.finish();

        } else {
            score.changeState(new GameScoring(this.score));
        }
    }

    private void defineWinner(Long playerId, Match match) {
        if(match.getPlayerOne().getId() == playerId) {
            match.setWinner(match.getPlayerOne());
        } else {
            match.setWinner(match.getPlayerTwo());
        }
    }

    public MatchScoring(Score score) {
        super(score);
    }

}


