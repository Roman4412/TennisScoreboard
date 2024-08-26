package com.pustovalov.service;

import com.pustovalov.dao.HibernatePlayerDao;
import com.pustovalov.dao.PlayerDao;
import com.pustovalov.entity.Player;

import java.util.Optional;

public class PlayerService {
  private static volatile PlayerService instance;
  private final PlayerDao hibernatePlayerDao;

  private PlayerService(PlayerDao playerDao) {
    this.hibernatePlayerDao = playerDao;
  }

  public static PlayerService getInstance() {
    if (instance == null) {
      synchronized (PlayerService.class) {
        if (instance == null) {
          instance = new PlayerService(HibernatePlayerDao.getInstance());
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
