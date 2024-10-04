package com.pustovalov.service;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.entity.Match;
import com.pustovalov.exception.MatchAlreadyPersistException;
import com.pustovalov.service.mapper.MatchMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersistenceMatchService {

    public static final int LIMIT = 10;

    private static volatile PersistenceMatchService instance;

    private final HibernateMatchDao hibernateMatchDao;

    private final OngoingMatchService ongoingMatchService;

    private final MatchMapper mapper;

    public PersistenceMatchService(HibernateMatchDao hibernateMatchDao, OngoingMatchService ongoingMatchService) {
        this.hibernateMatchDao = hibernateMatchDao;
        this.ongoingMatchService = ongoingMatchService;
        this.mapper = MatchMapper.INSTANCE;
    }

    public static PersistenceMatchService getInstance() {
        if (instance == null) {
            synchronized (MatchScoringService.class) {
                if (instance == null) {
                    instance = new PersistenceMatchService(HibernateMatchDao.getInstance(),
                            OngoingMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    public void save(UUID matchId) {
        Optional<Match> match = ongoingMatchService.getMatch(matchId);
        if (match.isEmpty() && hibernateMatchDao.isExist(matchId)) {
            throw new MatchAlreadyPersistException(String.format("The match(id = %s) has already been saved", matchId));
        } else {
            hibernateMatchDao.save(match.get());
            ongoingMatchService.delete(matchId);
        }
    }

    public StoredMatchesDto findAll(int page) {
        int totalPages = getTotalPages();
        if (page < 0 || page > totalPages) {
            throw new IllegalArgumentException("Invalid page number");
        }
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findAll(offset, LIMIT);

        return mapper.toStoredMatchesDto(matches, totalPages, page, page == totalPages);
    }

    public StoredMatchesDto findAll(int page, String name) {
        int totalPages = getTotalPages(name);
        if (page < 0 || page > totalPages) {
            throw new IllegalArgumentException("Invalid page number");
        }
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findByPlayerName(offset, LIMIT, name);

        return mapper.toStoredMatchesDto(matches, name, totalPages, page, page == totalPages);
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) hibernateMatchDao.getRowsAmount() / LIMIT);
    }

    private int getTotalPages(String name) {
        return (int) Math.ceil((double) hibernateMatchDao.getRowsAmount(name) / LIMIT);
    }
}
