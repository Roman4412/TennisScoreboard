package com.pustovalov.dao;

import com.pustovalov.entity.Player;
import java.util.Optional;

public interface PlayerDao {
    Player save(Player player);

    Optional<Player> findByName(String name);

}
