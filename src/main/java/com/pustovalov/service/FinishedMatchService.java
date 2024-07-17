package com.pustovalov.service;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.entity.Match;

public class FinishedMatchService {
    private static volatile FinishedMatchService instance;
    private final HibernateMatchDao hibernateMatchDao;


    public Match persist(Match match) {
        return hibernateMatchDao.save(match);
    }

    public static FinishedMatchService getInstance() {
        if (instance == null) {
            synchronized(FinishedMatchService.class) {
                if (instance == null) {
                    instance = new FinishedMatchService(HibernateMatchDao.getInstance());
                }
            }
        }
        return instance;
    }

    public FinishedMatchService(HibernateMatchDao hibernateMatchDao) {
        this.hibernateMatchDao = hibernateMatchDao;
    }

}
