package com.pustovalov.service;

import com.pustovalov.dao.MatchDao;
import com.pustovalov.entity.Match;

import java.util.List;

public class StoredMatchService {
    private final MatchDao matchDao;

    public List<Match> findAll(int offset, int limit) {
        return matchDao.findAll(offset, limit);
    }

    public List<Match> getMatchesFilterByName(int offset, int limit, String name) {
        return matchDao.findByPlayerName(offset, limit, name);
    }

    public int getNumOfPages(int limit) {
        Long totalMatches = matchDao.getNumOfMatches();
        return (int) Math.ceil(totalMatches / limit);
    }
    public int getNumOfPages(int limit, String name) {
        Long totalMatches = matchDao.getNumOfMatchesByName(name);
        int ceil = (int) Math.ceil(totalMatches / limit);
        return ceil == 0? 1 : ceil;
    }
    public StoredMatchService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

}
