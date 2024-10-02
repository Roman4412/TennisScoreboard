package com.pustovalov.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.entity.Player;

import java.util.Optional;

public class PlayerPersistenceService {

    private static volatile PlayerPersistenceService instance;

    private final PlayerDao hibernatePlayerDao;

    private PlayerPersistenceService(PlayerDao playerDao) {
        this.hibernatePlayerDao = playerDao;
    }

    public static PlayerPersistenceService getInstance() {
        if (instance == null) {
            synchronized (PlayerPersistenceService.class) {
                if (instance == null) {
                    instance = new PlayerPersistenceService(HibernatePlayerDao.getInstance());
                }
            }
        }
        return instance;
    }

    public Optional<Player> findBy(String name) {
        return hibernatePlayerDao.findByName(name);
    }

    public Player persist(Player player) {
        return hibernatePlayerDao.save(player);
    }
}
