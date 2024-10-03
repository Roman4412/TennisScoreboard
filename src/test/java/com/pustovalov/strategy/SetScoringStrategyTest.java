package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SetScoringStrategyTest {

    private final long playerOneId = 1L;

    private final long playerTwoId = 2L;

    private ScoringStrategy out;

    private Score score;

    @BeforeEach
    void prepare() {
        Player playerOne = new Player(playerOneId, "playerOneName");
        Player playerTwo = new Player(playerTwoId, "playerTwoName");
        Match match = new Match(playerOne, playerTwo, UUID.randomUUID());

        score = new Score(playerOneId, playerTwoId);
        score.setMatch(match);
        match.setScore(score);

        out = score.getScoringStrategy();
    }

    @Test
    void countWhenPlayerWinsGameThenSetScoreIncrements() {
        score.addPoint(playerOneId, "40", PointUnits.GAME);

        out.count(playerOneId);

        String playerOneActual = score.getPoint(playerOneId, PointUnits.SET).getValue();
        String playerTwoActual = score.getPoint(playerTwoId, PointUnits.SET).getValue();
        assertAll(() -> assertEquals("1", playerOneActual, "Incorrect scoring by the playerOne"),
                () -> assertEquals("0", playerTwoActual, "Incorrect scoring by the playerTwo"));
    }

    @Test
    void countWhenConditionsAreMetThenTiebreakBegins() {
        score.addPoint(playerOneId, "40", PointUnits.GAME);
        score.addPoint(playerOneId, "5", PointUnits.SET);
        score.addPoint(playerTwoId, "6", PointUnits.SET);

        out.count(playerOneId);

        ScoringStrategy actual = score.getScoringStrategy();
        assertInstanceOf(TiebreakScoringStrategy.class, actual);
    }

    @Test
    void countWhenPlayerWinsSetThenGamePointsAreReset() {
        String expected = "0";
        score.addPoint(playerOneId, "40", PointUnits.GAME);
        score.addPoint(playerOneId, "5", PointUnits.SET);

        out.count(playerOneId);

        String playerOneActual = score.getPoint(playerOneId, PointUnits.GAME).getValue();
        String playerTwoActual = score.getPoint(playerTwoId, PointUnits.GAME).getValue();
        assertAll(() -> assertEquals(expected, playerOneActual, "The points of the playerOne were not reset"),
                () -> assertEquals(expected, playerTwoActual, "The points of the playerTwo were not reset"));
    }

}
