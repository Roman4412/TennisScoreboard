package com.pustovalov.service.mapper;

import com.pustovalov.entity.Match;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.strategy.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MatchResultHelper {
    @Named(value = "getPlayerOneSetResult")
    default List<String> getPlayerOneSetResult(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerOne().getId();
        return score.getResultPoints(id, ScoreUnits.SET);
    }

    @Named(value = "getPlayerTwoSetResult")
    default List<String> getPlayerTwoSetResult(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerTwo().getId();
        return score.getResultPoints(id, ScoreUnits.SET);
    }

    @Named(value = "getPlayerOneMatchResult")
    default String getPlayerOneMatchResult(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerOne().getId();
        return score.getResultPoints(id, ScoreUnits.MATCH).get(0);
    }

    @Named(value = "getPlayerTwoMatchResult")
    default String getPlayerTwoMatchResult(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerTwo().getId();
        return score.getResultPoints(id, ScoreUnits.MATCH).get(0);
    }

}
