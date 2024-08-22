package com.pustovalov.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.InMemoryMatchDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.dto.NewMatchDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.strategy.Score;

import java.util.UUID;

public class OngoingMatchService {
    private static volatile OngoingMatchService instance;
    private final PlayerDao hibernatePlayerDao;
    private final InMemoryMatchDao inMemoryMatchDao;

    public Match saveInMemory(NewMatchDto newMatchDto) {
        Player playerOne = hibernatePlayerDao
                .findByName(newMatchDto.getPlayerOneName())
                .orElse(hibernatePlayerDao.save(new Player(newMatchDto.getPlayerOneName())));

        Player playerTwo = hibernatePlayerDao
                .findByName(newMatchDto.getPlayerTwoName())
                .orElse(hibernatePlayerDao.save(new Player(newMatchDto.getPlayerTwoName())));

        Match match = new Match(playerOne, playerTwo, UUID.randomUUID());
        match.setScore(new Score());

        return inMemoryMatchDao.save(match);
    }

    public Match get(UUID uuid) {
        return inMemoryMatchDao.findById(uuid).orElseThrow();
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

    public void delete(UUID uuid) {
        inMemoryMatchDao.delete(uuid);
    }

    private OngoingMatchService(PlayerDao hibernatePlayerDao, InMemoryMatchDao inMemoryMatchDao) {
        this.hibernatePlayerDao = hibernatePlayerDao;
        this.inMemoryMatchDao = inMemoryMatchDao;
    }

}