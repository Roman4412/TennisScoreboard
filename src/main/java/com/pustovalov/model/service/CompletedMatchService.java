package com.pustovalov.model.service;

import com.pustovalov.dao.HibernateMatchDao;

public class CompletedMatchService {
    private static volatile CompletedMatchService instance;

    private final HibernateMatchDao hibernateMatchDao;
    public static CompletedMatchService getInstance() {
        if (instance == null) {
            synchronized(CompletedMatchService.class) {
                if (instance == null) {
                    instance = new CompletedMatchService(HibernateMatchDao.getInstance());
                }
            }
        }
        return instance;
    }

    public CompletedMatchService(HibernateMatchDao hibernateMatchDao) {
        this.hibernateMatchDao = hibernateMatchDao;
    }

}
