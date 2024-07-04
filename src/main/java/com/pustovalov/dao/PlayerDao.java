package com.pustovalov.dao;

import com.pustovalov.HibernateUtil;
import com.pustovalov.entity.Player;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;


public class PlayerDao implements GenericDao<Player, Long> {
    private static volatile PlayerDao instance;
    private final SessionFactory sessionFactory;

    @Override
    public Player save(Player entity) {
        sessionFactory.getCurrentSession().persist(entity);
        return entity;
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public Optional<Player> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Player> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Player p where p.name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public void update(Player entity) {

    }

    @Override
    public void delete(Long id) {

    }

    public static PlayerDao getInstance() {
        if (instance == null) {
            synchronized(PlayerDao.class) {
                instance = new PlayerDao(HibernateUtil.getSessionFactory());
            }
        }
        return instance;
    }

    private PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
