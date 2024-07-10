package com.pustovalov.model.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.InMemoryMatchDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.model.dto.CreateMatchDto;
import com.pustovalov.model.entity.Match;
import com.pustovalov.model.pojo.MatchScore;
import com.pustovalov.model.entity.Player;

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

        return inMemoryMatchDao.save(Match.builder()
                .externalId(UUID.randomUUID())
                .playerOne(playerOne)
                .playerTwo(playerTwo)
                .score(new MatchScore(playerOne.getId(), playerTwo.getId()))
                .build());
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

    public Match get(UUID matchUuid) {
        return inMemoryMatchDao.findById(matchUuid).orElseThrow();
    }

}
