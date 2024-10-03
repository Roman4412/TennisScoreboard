package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoringStrategyTest {

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
    void countIfPlayerWinsSetThenMatchScoreIncrementsAndOpponentScoreUnchanged() {
        score.addPoint(playerOneId, "40", PointUnits.GAME);
        score.addPoint(playerOneId, "5", PointUnits.SET);

        out.count(playerOneId);

        String playerOneActual = score.getPoint(playerOneId, PointUnits.MATCH).getValue();
        String playerTwoActual = score.getPoint(playerTwoId, PointUnits.MATCH).getValue();
        assertAll(() -> assertEquals("1", playerOneActual, "Incorrect scoring by the playerOne"),
                () -> assertEquals("0", playerTwoActual, "Incorrect scoring by the playerTwo"));
    }

    @Test
    void countWhenSetIsFinishedThenStatisticsAreSaved() {
        score.addPoint(playerOneId, "5", PointUnits.SET);
        score.addPoint(playerOneId, "40", PointUnits.GAME);
        score.addPoint(playerTwoId, "1", PointUnits.SET);

        out.count(playerOneId);

        String playerOneResultActual = score.getResultScore(playerOneId, PointUnits.SET).get(0);
        String playerTwoResultActual = score.getResultScore(playerTwoId, PointUnits.SET).get(0);
        String playerOneResultExpected = "6";
        String playerTwoResultExpected = "1";
        String playerOneCurrentActual = score.getPoint(playerOneId, PointUnits.SET).getValue();
        String playerTwoCurrentActual = score.getPoint(playerTwoId, PointUnits.SET).getValue();
        String currentExpected = "0";
        assertAll(() -> assertEquals(playerOneResultExpected, playerOneResultActual,
                        "The score of the playerOne has not been saved"),

                () -> assertEquals(playerTwoResultExpected, playerTwoResultActual,
                        "The score of the playerTwo has not been saved"),

                () -> assertEquals(currentExpected, playerOneCurrentActual, "The score of the playerOne is not reset"),

                () -> assertEquals(currentExpected, playerTwoCurrentActual, "The score of the playerTwo is not reset"));
    }

    @Test
    void countWhenConditionsAreMetThenMatchIsFinished() {
        score.addPoint(playerOneId, "5", PointUnits.SET);
        score.addPoint(playerOneId, "1", PointUnits.MATCH);
        score.addPoint(playerOneId, "40", PointUnits.GAME);

        out.count(playerOneId);

        boolean isSavedScore = score.getResultScore(playerOneId, PointUnits.MATCH).contains("2");

        assertAll(() -> assertTrue(isSavedScore, "The score must be saved"),
                () -> assertTrue(score.getMatch().isFinished(), "The match must be completed"),
                () -> assertNotNull(score.getMatch().getWinner(), "Победитель должен быть определен"));

    }

    @Test
    void count() {

    }
}
