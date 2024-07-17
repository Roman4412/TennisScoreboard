package com.pustovalov.dao;

import com.pustovalov.entity.Match;
import com.pustovalov.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class HibernateMatchDao implements MatchDao<UUID> {
    private static volatile HibernateMatchDao instance;
    private final SessionFactory sessionFactory;

    public static HibernateMatchDao getInstance() {
        if (instance == null) {
            synchronized(HibernateMatchDao.class) {
                if (instance == null) {
                    instance = new HibernateMatchDao(HibernateUtil.getSessionFactory());
                }
            }
        }
        return instance;
    }

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Match save(Match match) {
        sessionFactory.getCurrentSession().persist(match);
        return match;
    }

    @Override
    public Optional<Match> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }

}
