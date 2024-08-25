package com.pustovalov.service;

import com.pustovalov.dto.MatchScoreDtoResp;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.strategy.Score;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MatchScoringService {

    private static volatile MatchScoringService instance;

    private final OngoingMatchService ongoingMatchService;

    private final PersistenceMatchService persistenceMatchService;

    private MatchScoringService(OngoingMatchService ongoingMatchService,
                                PersistenceMatchService finishedMatchPersistenceService) {
        this.ongoingMatchService = ongoingMatchService;
        this.persistenceMatchService = finishedMatchPersistenceService;
    }

    public static MatchScoringService getInstance() {
        if (instance == null) {
            synchronized(MatchScoringService.class) {
                if (instance == null) {
                    instance = new MatchScoringService(OngoingMatchService.getInstance(),
                            PersistenceMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    public MatchScoreDtoResp countPoint(Long playerId, UUID uuid) {
        Match match = ongoingMatchService.getMatch(uuid);

        Score score = match.getScore();
        score.count(playerId);

        Player playerOne = match.getPlayerOne();
        Player playerTwo = match.getPlayerTwo();

        return MatchScoreDtoResp.builder()
                .uuid(match.getExternalId().toString())
                .playerOne(playerOne)
                .playerOneGamePts(score.getPoints(playerOne.getId(), ScoreUnits.GAME))
                .playerOneSetPts(score.getPoints(playerOne.getId(), ScoreUnits.SET))
                .playerOneMatchPts(score.getPoints(playerOne.getId(), ScoreUnits.MATCH))
                .playerOneTiebreakPts(score.getPoints(playerOne.getId(), ScoreUnits.TIEBREAK))
                .playerTwo(playerTwo)
                .playerTwoGamePts(score.getPoints(playerTwo.getId(), ScoreUnits.GAME))
                .playerTwoSetPts(score.getPoints(playerTwo.getId(), ScoreUnits.SET))
                .playerTwoMatchPts(score.getPoints(playerTwo.getId(), ScoreUnits.MATCH))
                .playerTwoTiebreakPts(score.getPoints(playerTwo.getId(), ScoreUnits.TIEBREAK))
                .isFinished(match.isFinished())
                .build();
    }

}
