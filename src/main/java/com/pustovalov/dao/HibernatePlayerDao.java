package com.pustovalov.dao;

import com.pustovalov.HibernateUtil;
import com.pustovalov.entity.Player;
import org.hibernate.SessionFactory;

import java.util.Optional;


public class HibernatePlayerDao implements PlayerDao {
    private static volatile HibernatePlayerDao instance;
    private final SessionFactory sessionFactory;

    @Override
    public Player save(Player entity) {
        sessionFactory.getCurrentSession().persist(entity);
        return entity;
    }

    @Override
    public Optional<Player> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Player p where p.name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public static HibernatePlayerDao getInstance() {
        if (instance == null) {
            synchronized(HibernatePlayerDao.class) {
                if (instance == null) {
                    instance = new HibernatePlayerDao(HibernateUtil.getSessionFactory());
                }
            }
        }
        return instance;
    }

    private HibernatePlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
