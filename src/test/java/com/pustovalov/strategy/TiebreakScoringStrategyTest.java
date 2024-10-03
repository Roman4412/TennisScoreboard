package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TiebreakScoringStrategyTest {

    private final long playerOneId = 1L;

    private final long playerTwoId = 2L;

    private ScoringStrategy out;

    private Score score;

    static Stream<Arguments> provideTiebreakScoreVariants() {
        return Stream.of(Arguments.of("6", "0", GameScoringStrategy.class),
                Arguments.of("6", "5", GameScoringStrategy.class), Arguments.of("8", "7", GameScoringStrategy.class),
                Arguments.of("6", "6", TiebreakScoringStrategy.class),
                Arguments.of("5", "0", TiebreakScoringStrategy.class),
                Arguments.of("8", "8", TiebreakScoringStrategy.class),
                Arguments.of("0", "6", TiebreakScoringStrategy.class),
                Arguments.of("0", "0", TiebreakScoringStrategy.class));
    }

    @BeforeEach
    void prepare() {
        Player playerOne = new Player(playerOneId, "playerOneName");
        Player playerTwo = new Player(playerTwoId, "playerTwoName");
        Match match = new Match(playerOne, playerTwo, UUID.randomUUID());

        score = new Score(playerOneId, playerTwoId);
        score.setMatch(match);
        match.setScore(score);

        score.setScoringStrategy(new TiebreakScoringStrategy(score));
        out = score.getScoringStrategy();
    }

    @ParameterizedTest
    @MethodSource("provideTiebreakScoreVariants")
    void countWhenConditionsAreMetThenTiebreakEnds(String playerOneInit, String playerTwoInit,
            Class<ScoringStrategy> strategy) {
        score.addPoint(playerOneId, playerOneInit, PointUnits.TIEBREAK);
        score.addPoint(playerTwoId, playerTwoInit, PointUnits.TIEBREAK);

        out.count(playerOneId);

        assertInstanceOf(strategy, score.getScoringStrategy());
    }

    @Test
    void countWhenPlayerWinsTiebreakThenWinsSet() {
        score.addPoint(playerOneId, "6", PointUnits.SET);
        score.changeStrategy(new TiebreakScoringStrategy(score));
        score.addPoint(playerOneId, "6", PointUnits.TIEBREAK);

        out.count(playerOneId);

        String actual = score.getPoint(playerOneId, PointUnits.MATCH).getValue();
        assertEquals("1", actual);
    }

}
