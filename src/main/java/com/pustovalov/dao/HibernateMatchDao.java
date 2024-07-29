package com.pustovalov.dao;

import com.pustovalov.entity.Match;
import com.pustovalov.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;
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

    public List<Match> findAll(int offset, int limit) {
        String queryString = "select m from Match m order by m.id desc";
        return sessionFactory.getCurrentSession()
                .createQuery(queryString, Match.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .list();

    }

    @Override
    public List<Match> findByPlayerName(int offset, int limit, String name) {
        String queryString = "select m from Match m where lower(playerOne.name) like lower(:name) or lower(playerTwo.name) like lower(:name) order by m.id desc";
        return sessionFactory.getCurrentSession()
                .createQuery(queryString, Match.class)
                .setParameter("name", "%" + name + "%")
                .setMaxResults(limit)
                .setFirstResult(offset)
                .list();
    }

    public Long getNumOfMatches() {
        String queryString = "select count(*) from Match";
        return sessionFactory.getCurrentSession().createQuery(queryString, Long.class).uniqueResult();
    }

    public Long getNumOfMatchesByName(String name) {
        String queryString = "select count(*) from Match m where lower(playerOne.name) like lower(:name) or lower(playerTwo.name) like lower(:name)";
        return sessionFactory.getCurrentSession()
                .createQuery(queryString, Long.class)
                .setParameter("name", "%" + name + "%")
                .uniqueResult();
    }

}