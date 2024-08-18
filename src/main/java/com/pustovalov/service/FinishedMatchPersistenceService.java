package com.pustovalov.service;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.entity.Match;

public class FinishedMatchPersistenceService {
    private static volatile FinishedMatchPersistenceService instance;
    private final HibernateMatchDao hibernateMatchDao;


    public Match persist(Match match) {
        return hibernateMatchDao.save(match);
    }

    public static FinishedMatchPersistenceService getInstance() {
        if (instance == null) {
            synchronized(FinishedMatchPersistenceService.class) {
                if (instance == null) {
                    instance = new FinishedMatchPersistenceService(HibernateMatchDao.getInstance());
                }
            }
        }
        return instance;
    }

    public FinishedMatchPersistenceService(HibernateMatchDao hibernateMatchDao) {
        this.hibernateMatchDao = hibernateMatchDao;
    }

}
