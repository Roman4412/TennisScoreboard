package com.pustovalov.service;

import com.pustovalov.dao.PlayerDao;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CurrentMatchService {
    PlayerDao playerDao = new PlayerDao();

    //TODO адаптировать к многопоточке
    Map<UUID, Match> currentMatches = new HashMap<>();

    public Match save(String playerOneName, String playerTwoName) {
        Player playerOne = playerDao.findByName(playerOneName)
                .orElse(playerDao.save(new Player(playerOneName)));
        Player playerTwo = playerDao.findByName(playerTwoName)
                .orElse(playerDao.save(new Player(playerTwoName)));

        Match match = new Match(UUID.randomUUID(), playerOne, playerTwo);
        currentMatches.put(match.getExternalId(), match);
        return match;
    }

}
