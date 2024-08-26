package com.pustovalov.service;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.service.mapper.MatchMapper;
import com.pustovalov.strategy.Score;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
  private static volatile OngoingMatchService instance;
  private final PlayerService playerService;
  private final Map<UUID, Match> currentMatches;
  private final MatchMapper mapper;

  private OngoingMatchService(PlayerService playerService) {
    this.playerService = playerService;
    currentMatches = new ConcurrentHashMap<>();
    mapper = MatchMapper.INSTANCE;
  }

  public static OngoingMatchService getInstance() {
    if (instance == null) {
      synchronized (OngoingMatchService.class) {
        if (instance == null) {
          instance = new OngoingMatchService(PlayerService.getInstance());
        }
      }
    }
    return instance;
  }

  public UUID create(CreateMatchDto createMatchDto) {
    String playerOneName = createMatchDto.getPlayerOneName();
    String playerTwoName = createMatchDto.getPlayerTwoName();

    if (playerOneName.equals(playerTwoName)) {
      throw new RuntimeException();
    }

    Player playerOne =
        playerService
            .findBy(playerOneName)
            .orElseGet(() -> playerService.persist(new Player(playerOneName)));

    Player playerTwo =
        playerService
            .findBy(playerTwoName)
            .orElseGet(() -> playerService.persist(new Player(playerTwoName)));

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
    Match removed = currentMatches.remove(uuid);
    if (removed == null) {
      throw new RuntimeException(String.format("The match with uuid %s was not found", uuid));
    }
  }
}
