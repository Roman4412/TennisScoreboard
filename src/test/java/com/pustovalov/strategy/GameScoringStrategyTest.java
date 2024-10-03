package com.pustovalov.strategy;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.PointUnits;
import com.pustovalov.entity.Score;
import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

public class GameScoringStrategyTest {

    public static final String METHOD_REF_PREFIX = "com.pustovalov.strategy.GameScoringStrategyTest#";

    //TODO after each method
    private static final String ZERO_PTS = "0";

    private static final String FIFTEEN_PTS = "15";

    private static final String THIRTY_PTS = "30";

    private static final String FORTY_PTS = "40";

    private static final String ADVANTAGE = "AD";

    public long playerOneId;

    public long playerTwoId;

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
    void init() {
        OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        UUID uuid = ongoingMatchService.create(new CreateMatchDto("playerOneName", "playerTwoName"));
        Match match = ongoingMatchService.getMatch(uuid).get();

        playerOneId = match.getPlayerOne().getId();
        playerTwoId = match.getPlayerTwo().getId();

        score = new Score(playerOneId, playerTwoId);
        score.setMatch(match);
        match.setScore(score);

        session.getTransaction().commit();

        out = score.getScoringStrategy();
    }

    @ParameterizedTest
    @MethodSource(METHOD_REF_PREFIX + "provideGameScoreVariants")
    void countIfPlayerWinsPointThenHisScoreIncrementsAndOpponentScoreUnchanged(String playerOneInit,
            String playerTwoInit, String playerOneExpected, String playerTwoExpected) {

        score.addPoint(playerOneId, playerOneInit, PointUnits.GAME);
        score.addPoint(playerTwoId, playerTwoInit, PointUnits.GAME);

        out.count(playerOneId);

        String playerOneActual = score.getPoint(playerOneId, PointUnits.GAME).getValue();
        String playerTwoActual = score.getPoint(playerTwoId, PointUnits.GAME).getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(playerOneExpected, playerOneActual, "Incorrect scoring by the playerOne"),
                () -> Assertions.assertEquals(playerTwoExpected, playerTwoActual,
                        "Incorrect scoring by the playerTwo"));
    }
}
