package com.pustovalov.dao;

import com.pustovalov.entity.Match;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
        Match removed = currentMatches.remove(id);
        if (removed == null) {
            throw new RuntimeException(String.format(
                    "The match with id %s was not found", id));
        }
    }

    @Override
    public List<Match> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public List<Match> findByPlayerName(int offset, int limit, String name) {
        return null;
    }

    @Override
    public Long getRowsAmount() {
        return 0L;
    }

    @Override
    public Long getRowsAmount(String name) {
        return null;
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