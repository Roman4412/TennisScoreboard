package com.pustovalov.service;

import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.entity.Score;
import com.pustovalov.exception.InvalidMatchPlayerException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
  private static volatile OngoingMatchService instance;
  private final PlayerPersistenceService playerPersistenceService;
  private final Map<UUID, Match> currentMatches;
  private OngoingMatchService(PlayerPersistenceService playerPersistenceService) {
    this.playerPersistenceService = playerPersistenceService;
    currentMatches = new ConcurrentHashMap<>();
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
    String playerOneName = createMatchDto.playerOneName();
    String playerTwoName = createMatchDto.playerTwoName();

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
    match.setScore(new Score(playerOne.getId(), playerTwo.getId()));
    currentMatches.put(uuid, match);

    return uuid;
  }

  public Optional<Match> getMatch(UUID uuid) {
    return Optional.ofNullable(currentMatches.get(uuid));
  }

  public void delete(UUID uuid) {
    currentMatches.remove(uuid);
  }
}
