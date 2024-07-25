package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.enums.ScoreUnits;

public class MatchScoringStrategy extends ScoringStrategy {

    @Override
    public void count(Long playerId) {
        int playerScore = Integer.parseInt(score.getPoints(playerId, ScoreUnits.MATCH));
        Match match = score.getMatch();

        score.saveScore(ScoreUnits.SET);
        score.resetScore(ScoreUnits.SET);
        score.setPoints(playerId, ScoreUnits.MATCH, String.valueOf(++playerScore));

        if (playerScore == 2) {
            score.saveScore(ScoreUnits.MATCH);
            match.setWinner(defineWinner(playerId));
            match.finish();
            System.out.println("FIRST: "+score.getMatchResults().get(playerId));
            System.out.println("SECOND: " + score.getMatchResults().get(score.getMatch().getOpponentId(playerId)));
        } else {
            score.changeStrategy(new GameScoringStrategy(this.score));
        }
    }

    private Player defineWinner(Long playerId) {
        Player playerOne = score.getMatch().getPlayerOne();
        Player playerTwo = score.getMatch().getPlayerTwo();

        return playerOne.getId().equals(playerId) ? playerOne : playerTwo;
    }

    public MatchScoringStrategy(Score score) {
        super(score);
    }

}