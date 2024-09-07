package com.pustovalov.service.mapper;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.entity.Match;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchMapper {
  MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

  @Mapping(source = "player-one-name", target = "playerOneName")
  @Mapping(source = "player-two-name", target = "playerTwoName")
  CreateMatchDto toCreateMatchDto(Map<String, String> params);

  @Mapping(source = "match.externalId", target = "uuid")
  MatchScoreDto toMatchScoreDto(Match match);

  StoredMatchesDto toStoredMatchesDto(
      List<Match> matches, int totalPages, int currentPage, boolean isLast);

  @Mapping(source = "name", target = "filterByPlayerName")
  StoredMatchesDto toStoredMatchesDto(
      List<Match> matches, String name, int totalPages, int currentPage, boolean isLast);
}
