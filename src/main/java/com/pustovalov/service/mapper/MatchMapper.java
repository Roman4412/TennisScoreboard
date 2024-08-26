package com.pustovalov.service.mapper;

import com.pustovalov.dto.response.MatchResultDto;
import com.pustovalov.entity.Match;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.strategy.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    /* return MatchResultDto.builder()
         .matchId(matchId)
         .playerOne(playerOne)
         .playerOneSetPts(score.getResultPoints(playerOne.getId(), ScoreUnits.SET))
         .playerOneMatchPts(score.getResultPoints(playerOne.getId(), ScoreUnits.MATCH))
         .playerTwo(playerTwo)
         .playerTwoSetPts(score.getResultPoints(playerTwo.getId(), ScoreUnits.SET))
         .playerTwoMatchPts(score.getResultPoints(playerTwo.getId(), ScoreUnits.MATCH))
         .build();*/
    @Mapping(source = "externalId", target = "matchId")
    @Mapping(source = "match", qualifiedByName = "playerOneSetPts", target = "playerOneSetPts")
    @Mapping(source = "match", qualifiedByName = "playerTwoSetPts", target = "playerTwoSetPts")
    @Mapping(source = "match", qualifiedByName = "playerOneMatchPts", target = "playerOneMatchPts")
    @Mapping(source = "match", qualifiedByName = "playerTwoMatchPts", target = "playerTwoMatchPts")
    MatchResultDto toMatchResultDto(Match match);

    @Named(value = "playerOneSetPts")
    default List<String> getPlayerOneSetPts(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerOne().getId();
        return score.getResultPoints(id, ScoreUnits.SET);
    }

    @Named(value = "playerTwoSetPts")
    default List<String> getPlayerTwoSetPts(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerTwo().getId();
        return score.getResultPoints(id, ScoreUnits.SET);
    }


    @Named(value = "playerOneMatchPts")
    default String getPlayerOneMatchPts(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerOne().getId();
        return score.getResultPoints(id, ScoreUnits.MATCH).get(0);
    }

    @Named(value = "playerTwoMatchPts")
    default String getPlayerTwoMatchPts(Match match) {
        Score score = match.getScore();
        Long id = match.getPlayerTwo().getId();
        return score.getResultPoints(id, ScoreUnits.MATCH).get(0);
    }

}