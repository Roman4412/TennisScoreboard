package com.pustovalov.service;

import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.service.mapper.MatchMapper;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatchScoringService {

  private static volatile MatchScoringService instance;

  private final OngoingMatchService ongoingMatchService;

  private final PersistenceMatchService persistenceMatchService;

  private final MatchMapper mapper;

  private MatchScoringService(
      OngoingMatchService ongoingMatchService, PersistenceMatchService persistenceMatchService) {
    this.ongoingMatchService = ongoingMatchService;
    this.persistenceMatchService = persistenceMatchService;
    mapper = MatchMapper.INSTANCE;
  }

  public static MatchScoringService getInstance() {
    if (instance == null) {
      synchronized (MatchScoringService.class) {
        if (instance == null) {
          instance =
              new MatchScoringService(
                  OngoingMatchService.getInstance(), PersistenceMatchService.getInstance());
        }
      }
    }
    return instance;
  }

  public void count(UUID matchId, Long playerId) {
    Match match =
        ongoingMatchService
            .getMatch(matchId)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format("The match with the uuid %s was not found", matchId)));

    match.getScore().getScoringStrategy().count(playerId);
  }

  public MatchScoreDto getScoreState(UUID matchId) {
    Match match =
        ongoingMatchService
            .getMatch(matchId)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        String.format("The match with the uuid %s was not found", matchId)));
    return mapper.toMatchScoreDto(match);
  }
}
