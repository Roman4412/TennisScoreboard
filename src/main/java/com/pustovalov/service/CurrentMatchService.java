package com.pustovalov.service;

import com.pustovalov.dao.InMemoryMatchDao;
import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.dto.CreateMatchDto;
import com.pustovalov.entity.GameScore;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;

import java.util.UUID;

public class CurrentMatchService {
    private static volatile CurrentMatchService instance;
    private final PlayerDao hibernatePlayerDao;
    private final InMemoryMatchDao inMemoryMatchDao;

    public Match saveInMemory(CreateMatchDto createMatchDto) {
        Player playerOne = hibernatePlayerDao
                .findByName(createMatchDto.playerOneName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.playerOneName())));

        Player playerTwo = hibernatePlayerDao
                .findByName(createMatchDto.playerTwoName())
                .orElse(hibernatePlayerDao.save(new Player(createMatchDto.playerTwoName())));

        return inMemoryMatchDao.save(Match.builder()
                .externalId(UUID.randomUUID())
                .playerOne(playerOne)
                .playerTwo(playerTwo)
                .score(new GameScore())
                .build());
    }

    public static CurrentMatchService getInstance() {
        if (instance == null) {
            synchronized(CurrentMatchService.class) {
                if (instance == null) {
                    instance = new CurrentMatchService(
                            HibernatePlayerDao.getInstance(),
                            InMemoryMatchDao.getInstance());
                }
            }
        }
        return instance;
    }

    private CurrentMatchService(PlayerDao hibernatePlayerDao, InMemoryMatchDao inMemoryMatchDao) {
        this.hibernatePlayerDao = hibernatePlayerDao;
        this.inMemoryMatchDao = inMemoryMatchDao;
    }

}
