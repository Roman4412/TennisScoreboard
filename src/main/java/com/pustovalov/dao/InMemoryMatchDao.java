package com.pustovalov.dao;

import com.pustovalov.entity.Match;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchDao implements MatchDao<UUID> {
    private static volatile InMemoryMatchDao instance;
    private final Map<UUID, Match> currentMatches;

    @Override
    public Match save(Match match) {
        currentMatches.put(match.getExternalId(), match);
        return match;
    }

    @Override
    public Optional<Match> findById(UUID id) {
        return Optional.ofNullable(currentMatches.get(id));
    }

    @Override
    public void delete(UUID id) {

    }
    public static InMemoryMatchDao getInstance() {
        if (instance == null) {
            synchronized(InMemoryMatchDao.class) {
                if (instance == null) {
                    instance = new InMemoryMatchDao();
                }
            }
        }
        return instance;
    }

    private InMemoryMatchDao() {
        currentMatches = new ConcurrentHashMap<>();
    }

}
