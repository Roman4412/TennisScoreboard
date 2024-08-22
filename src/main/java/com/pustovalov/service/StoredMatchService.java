package com.pustovalov.service;

import com.pustovalov.dao.MatchDao;
import com.pustovalov.dto.StoredMatchResponse;
import com.pustovalov.entity.Match;

import java.util.List;

public class StoredMatchService {
    public static final int LIMIT = 10;
    private final MatchDao matchDao;

    public StoredMatchResponse findAll(int page) {
        int totalPages = getTotalPages();
        int offset = page * LIMIT;
        List<Match> matches = matchDao.findAll(offset, LIMIT);

        return StoredMatchResponse.builder()
                .totalPages(totalPages)
                .matches(matches)
                .currentPage(page)
                .build();
    }

    public StoredMatchResponse findAll(int page, String name) {
        int totalPages = getTotalPages(name);
        int offset = page * LIMIT;
        List<Match> matches = matchDao.findByPlayerName(offset, LIMIT, name);

        return StoredMatchResponse.builder()
                .totalPages(totalPages)
                .matches(matches)
                .filterByPlayerName(name)
                .currentPage(page)
                .build();
    }

    private int getTotalPages() {
        Long total = matchDao.getRowsAmount();
        return (int) (total / LIMIT);
    }

    private int getTotalPages(String name) {
        Long total = matchDao.getRowsAmount(name);
        return (int) (total / LIMIT);
    }

    public StoredMatchService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

}