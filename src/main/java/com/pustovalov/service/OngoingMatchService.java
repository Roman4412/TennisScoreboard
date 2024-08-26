package com.pustovalov.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.dto.request.CreateMatchDto;
import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.strategy.Score;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
    private static volatile OngoingMatchService instance;
    private final PlayerDao hibernatePlayerDao;
    private final Map<UUID, Match> currentMatches;

    private OngoingMatchService(PlayerDao hibernatePlayerDao) {
        this.hibernatePlayerDao = hibernatePlayerDao;
        currentMatches = new ConcurrentHashMap<>();
    }

    public static OngoingMatchService getInstance() {
        if (instance == null) {
            synchronized(OngoingMatchService.class) {
                if (instance == null) {
                    instance = new OngoingMatchService(HibernatePlayerDao.getInstance());
                }
            }
        }
        return instance;
    }

    public UUID create(CreateMatchDto createMatchDto) {
        Player playerOne = hibernatePlayerDao.findByName(createMatchDto.getPlayerOneName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.getPlayerOneName())));

        Player playerTwo = hibernatePlayerDao.findByName(createMatchDto.getPlayerTwoName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.getPlayerTwoName())));

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
        Match match = getMatch(uuid);
        Score score = match.getScore();
        Player playerOne = match.getPlayerOne();
        Player playerTwo = match.getPlayerTwo();
        return MatchScoreDto.builder()
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

    public void delete(UUID uuid) {
        Match removed = currentMatches.remove(uuid);
        if (removed == null) {
            throw new RuntimeException(String.format("The match with uuid %s was not found", uuid));
        }
    }

}