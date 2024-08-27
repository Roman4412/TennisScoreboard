package com.pustovalov.service;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.exception.InvalidMatchPlayerException;
import com.pustovalov.service.mapper.MatchMapper;
import com.pustovalov.strategy.Score;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
  private static volatile OngoingMatchService instance;
  private final PlayerPersistenceService playerPersistenceService;
  private final Map<UUID, Match> currentMatches;
  private final MatchMapper mapper;

  private OngoingMatchService(PlayerPersistenceService playerPersistenceService) {
    this.playerPersistenceService = playerPersistenceService;
    currentMatches = new ConcurrentHashMap<>();
    mapper = MatchMapper.INSTANCE;
  }

  public static OngoingMatchService getInstance() {
    if (instance == null) {
      synchronized (OngoingMatchService.class) {
        if (instance == null) {
          instance = new OngoingMatchService(PlayerPersistenceService.getInstance());
        }
      }
    }
    return instance;
  }

  public UUID create(CreateMatchDto createMatchDto) {
    String playerOneName = createMatchDto.getPlayerOneName();
    String playerTwoName = createMatchDto.getPlayerTwoName();

    if (playerOneName.equals(playerTwoName)) {
      throw new InvalidMatchPlayerException("The player cannot play with himself");
    }

    Player playerOne =
        playerPersistenceService
            .findBy(playerOneName)
            .orElseGet(() -> playerPersistenceService.persist(new Player(playerOneName)));

    Player playerTwo =
        playerPersistenceService
            .findBy(playerTwoName)
            .orElseGet(() -> playerPersistenceService.persist(new Player(playerTwoName)));

    UUID uuid = UUID.randomUUID();
    Match match = new Match(playerOne, playerTwo, uuid);
    match.setScore(new Score());
    currentMatches.put(uuid, match);

    return uuid;
  }

  public Match getMatch(UUID uuid) {
    if (!currentMatches.containsKey(uuid)) {
      throw new NoSuchElementException();
    }
    return currentMatches.get(uuid);
  }

  public MatchScoreDto getMatchForView(UUID uuid) {
    return mapper.toMatchScoreDto(getMatch(uuid));
  }

  public void delete(UUID uuid) {
    currentMatches.remove(uuid);
  }
}
