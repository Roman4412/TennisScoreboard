package com.pustovalov.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.InMemoryMatchDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.dto.CreateMatchDto;
import com.pustovalov.strategy.Score;

import java.util.UUID;

public class OngoingMatchService {
    private static volatile OngoingMatchService instance;
    private final PlayerDao hibernatePlayerDao;
    private final InMemoryMatchDao inMemoryMatchDao;

    public Match saveInMemory(CreateMatchDto createMatchDto) {
        Player playerOne = hibernatePlayerDao
                .findByName(createMatchDto.playerOneName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.playerOneName())));

        Player playerTwo = hibernatePlayerDao
                .findByName(createMatchDto.playerTwoName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.playerTwoName())));

        Match match = new Match(playerOne, playerTwo, UUID.randomUUID());
        match.setScore(new Score());

        return inMemoryMatchDao.save(match);
    }

    public Match get(UUID matchUuid) {
        return inMemoryMatchDao.findById(matchUuid).orElseThrow();
    }

    public static OngoingMatchService getInstance() {
        if (instance == null) {
            synchronized(OngoingMatchService.class) {
                if (instance == null) {
                    instance = new OngoingMatchService(
                            HibernatePlayerDao.getInstance(),
                            InMemoryMatchDao.getInstance());
                }
            }
        }
        return instance;
    }

    private OngoingMatchService(PlayerDao hibernatePlayerDao, InMemoryMatchDao inMemoryMatchDao) {
        this.hibernatePlayerDao = hibernatePlayerDao;
        this.inMemoryMatchDao = inMemoryMatchDao;
    }

    public void delete(UUID uuid) {
        inMemoryMatchDao.delete(uuid);
    }

}