package com.pustovalov.service.mapper;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.dto.response.MatchResultDto;
import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.entity.Match;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {MatchResultHelper.class, MatchScoreHelper.class})
public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "player-one-name", target = "playerOneName")
    @Mapping(source = "player-two-name", target = "playerTwoName")
    CreateMatchDto toCreateMatchDto(Map<String, String> params);

    @Mapping(source = "externalId", target = "matchId")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneSetResult",
             target = "playerOneSetPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoSetResult",
             target = "playerTwoSetPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerOneMatchResult",
             target = "playerOneMatchPts")
    @Mapping(source = "match", qualifiedByName = "getPlayerTwoMatchResult",
             target = "playerTwoMatchPts")
    MatchResultDto toMatchResultDto(Match match);
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
    StoredMatchesDto toStoredMatchesDto(List<Match> matches, int totalPages, int currentPage);
    @Mapping(source = "name", target = "filterByPlayerName")
    StoredMatchesDto toStoredMatchesDto(List<Match> matches, String name, int totalPages,
                                        int currentPage);

}