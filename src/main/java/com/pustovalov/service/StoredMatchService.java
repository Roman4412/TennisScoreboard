package com.pustovalov.service;

import com.pustovalov.dao.MatchDao;
import com.pustovalov.dto.StoredMatchDto;
import com.pustovalov.entity.Match;

import java.util.ArrayList;
import java.util.List;

public class StoredMatchService {
    private final MatchDao matchDao;

    public List<Match> findAll(int offset, int limit) {
        return matchDao.findAll(offset, limit);
    }

    public List<StoredMatchDto> getMatchesFilterByName(int offset, int limit, String name) {
        matchDao.findAll(offset, limit);

        return new ArrayList<StoredMatchDto>();
    }

    public StoredMatchService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

}
