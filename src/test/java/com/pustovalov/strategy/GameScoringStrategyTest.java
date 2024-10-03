package com.pustovalov.strategy;

import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameScoringStrategyTest {
    private static final String ZERO_PTS = "0";

    private static final String FIFTEEN_PTS = "15";

    private static final String THIRTY_PTS = "30";

    private static final String FORTY_PTS = "40";

    private static final String ADVANTAGE = "AD";

    private final long playerOneId = 1L;

    private final long playerTwoId = 2L;

    private ScoringStrategy out;

    private Score score;

    static Stream<Arguments> provideGameScoreVariants() {
        return Stream.of(Arguments.of(ZERO_PTS, ZERO_PTS, FIFTEEN_PTS, ZERO_PTS),
                Arguments.of(FIFTEEN_PTS, ZERO_PTS, THIRTY_PTS, ZERO_PTS),
                Arguments.of(THIRTY_PTS, ZERO_PTS, FORTY_PTS, ZERO_PTS),
                Arguments.of(FORTY_PTS, ZERO_PTS, ZERO_PTS, ZERO_PTS),
                Arguments.of(FORTY_PTS, FORTY_PTS, ADVANTAGE, FORTY_PTS),
                Arguments.of(ADVANTAGE, FORTY_PTS, ZERO_PTS, ZERO_PTS),
                Arguments.of(FORTY_PTS, ADVANTAGE, FORTY_PTS, FORTY_PTS));
    }

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

    @ParameterizedTest
    @MethodSource("provideGameScoreVariants")
    void countIfPlayerWinsPointThenHisScoreIncrementsAndOpponentScoreUnchanged(String playerOneInit,
            String playerTwoInit, String playerOneExpected, String playerTwoExpected) {

        score.addPoint(playerOneId, playerOneInit, PointUnits.GAME);
        score.addPoint(playerTwoId, playerTwoInit, PointUnits.GAME);

        out.count(playerOneId);

        String playerOneActual = score.getPoint(playerOneId, PointUnits.GAME).getValue();
        String playerTwoActual = score.getPoint(playerTwoId, PointUnits.GAME).getValue();
        assertAll(() -> assertEquals(playerOneExpected, playerOneActual, "Incorrect scoring by the playerOne"),
                () -> assertEquals(playerTwoExpected, playerTwoActual, "Incorrect scoring by the playerTwo"));
    }

}
