package com.pustovalov.dao;

import com.pustovalov.entity.Match;
import com.pustovalov.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

public class HibernateMatchDao implements MatchDao {

    private static volatile HibernateMatchDao instance;

    private final SessionFactory sessionFactory;

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static HibernateMatchDao getInstance() {
        if (instance == null) {
            synchronized (HibernateMatchDao.class) {
                if (instance == null) {
                    instance = new HibernateMatchDao(HibernateUtil.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public Match save(Match match) {
        sessionFactory.getCurrentSession().persist(match);
        return match;
    }

    public List<Match> findAll(int offset, int limit) {
        String queryString = "select m from Match m join fetch m.playerOne join fetch m.playerTwo order by m.id desc";
        return sessionFactory.getCurrentSession().createQuery(queryString, Match.class).setMaxResults(limit)
                .setFirstResult(offset).list();
    }

    @Override
    public List<Match> findByPlayerName(int offset, int limit, String name) {
        String queryString = "select m from Match m join fetch m.playerOne join fetch m.playerTwo where lower(m .playerOne.name) " + "like lower(:name) or lower(m.playerTwo.name) like lower(:name) order by m.id desc";
        return sessionFactory.getCurrentSession().createQuery(queryString, Match.class).setParameter("name",
                "%" + name + "%").setMaxResults(limit).setFirstResult(offset).list();
    }

    public Long getRowsAmount() {
        String queryString = "select count(*) from Match";
        return sessionFactory.getCurrentSession().createQuery(queryString, Long.class).uniqueResult();
    }

    public Long getRowsAmount(String name) {
        String queryString = "select count(*) from Match m where lower(playerOne.name) like lower(:name) or lower(playerTwo.name) " + "like lower(:name)";
        return sessionFactory.getCurrentSession().createQuery(queryString, Long.class).setParameter("name",
                "%" + name + "%").uniqueResult();
    }

    public boolean isExist(UUID matchId) {
        String queryString = "select 1 from Match where externalId =:id";
        return sessionFactory.getCurrentSession().createQuery(queryString, boolean.class).setParameter("id", matchId)
                       .uniqueResult() != null;
    }

}
