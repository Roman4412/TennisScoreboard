package com.pustovalov.service.mapper;

import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.strategy.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface MatchScoreMapper {
    @Mapping(source = "match.externalId", target = "uuid")
    @Mapping(source = "match", qualifiedByName = "getPlayerOne", target = "playerOne")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneGamePts", target = "playerOneGamePts")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneSetPts", target = "playerOneSetPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneMatchPts",
             target = "playerOneMatchPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneTiebreakPts",
             target = "playerOneTiebreakPts")

    @Mapping(source = "match", qualifiedByName = "getPlayerTwo", target = "playerTwo")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoGamePts", target = "playerTwoGamePts")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoSetPts", target = "playerTwoSetPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoMatchPts",
             target = "playerTwoMatchPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoTiebreakPts",
             target = "playerTwoTiebreakPts")
    MatchScoreDto toMatchScoreDto(Match match);

    @Named(value = "getPlayerOne")
    default Player getPlayerOneName(Match match) {
        return match.getPlayerOne();
    }

    @Named(value = "getPlayerOneGamePts")
    default String getPlayerOneGamePts(Match match) {
        Long id = match.getPlayerOne().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.GAME);
    }

    @Named(value = "getPlayerOneSetPts")
    default String getPlayerOneSetPts(Match match) {
        Long id = match.getPlayerOne().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.SET);
    }

    @Named(value = "getPlayerOneMatchPts")
    default String getPlayerOneMatchPts(Match match) {
        Long id = match.getPlayerOne().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.MATCH);
    }

    @Named(value = "getPlayerOneTiebreakPts")
    default String getPlayerOneTiebreakPts(Match match) {
        Long id = match.getPlayerOne().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.TIEBREAK);
    }

    @Named(value = "getPlayerTwo")
    default Player getPlayerTwo(Match match) {
        return match.getPlayerTwo();
    }

    @Named(value = "getPlayerTwoGamePts")
    default String getPlayerTwoGamePts(Match match) {
        Long id = match.getPlayerTwo().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.GAME);
    }

    @Named(value = "getPlayerTwoSetPts")
    default String getPlayerTwoSetPts(Match match) {
        Long id = match.getPlayerTwo().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.SET);
    }

    @Named(value = "getPlayerTwoMatchPts")
    default String getPlayerTwoMatchPts(Match match) {
        Long id = match.getPlayerTwo().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.MATCH);
    }

    @Named(value = "getPlayerTwoTiebreakPts")
    default String getPlayerTwoTiebreakPts(Match match) {
        Long id = match.getPlayerTwo().getId();
        Score score = match.getScore();

        return score.getPoints(id, ScoreUnits.TIEBREAK);
    }
}
