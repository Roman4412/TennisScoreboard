package com.pustovalov.service;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.dto.response.MatchResultDto;
import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.entity.Match;
import com.pustovalov.service.mapper.MatchMapper;
import java.util.List;
import java.util.UUID;

public class PersistenceMatchService {
    public static final int LIMIT = 10;
    private static volatile PersistenceMatchService instance;
    private final HibernateMatchDao hibernateMatchDao;
    private final OngoingMatchService ongoingMatchService;
    private final MatchMapper mapper;

    public PersistenceMatchService(HibernateMatchDao hibernateMatchDao,
                                   OngoingMatchService ongoingMatchService) {
        this.hibernateMatchDao = hibernateMatchDao;
        this.ongoingMatchService = ongoingMatchService;
        this.mapper = MatchMapper.INSTANCE;
    }

    public static PersistenceMatchService getInstance() {
        if (instance == null) {
            synchronized(MatchScoringService.class) {
                if (instance == null) {
                    instance = new PersistenceMatchService(HibernateMatchDao.getInstance(),
                            OngoingMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    public MatchResultDto save(UUID matchId) {
        Match match = ongoingMatchService.getMatch(matchId);
        hibernateMatchDao.save(match);
        ongoingMatchService.delete(matchId);

        return mapper.toMatchResultDto(match);
    }

    public StoredMatchesDto findAll(int page) {
        int totalPages = getTotalPages();
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findAll(offset, LIMIT);

        return mapper.toStoredMatchesDto(matches, totalPages, page);
    }

    public StoredMatchesDto findAll(int page, String name) {
        int totalPages = getTotalPages(name);
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findByPlayerName(offset, LIMIT, name);

        return mapper.toStoredMatchesDto(matches, name, totalPages, page);
    }

    private int getTotalPages() {
        return (int) (hibernateMatchDao.getRowsAmount() / LIMIT);
    }

    private int getTotalPages(String name) {
        return (int) (hibernateMatchDao.getRowsAmount(name) / LIMIT);
    }

}