package com.pustovalov.dao;

import com.pustovalov.entity.Match;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchDao {
    private static volatile InMemoryMatchDao instance;
    private final Map<UUID, Match> currentMatches;

    public Match save(Match match) {
        currentMatches.put(match.getExternalId(), match);
        return match;
    }

    public Optional<Match> findById(UUID uuid) {
        return Optional.ofNullable(currentMatches.get(uuid));
    }

    public void delete(UUID uuid) {
        Match removed = currentMatches.remove(uuid);
        if (removed == null) {
            throw new RuntimeException(String.format(
                    "The match with uuid %s was not found", uuid));
        }
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