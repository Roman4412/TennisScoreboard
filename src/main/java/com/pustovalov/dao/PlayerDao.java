package com.pustovalov.dao;

import com.pustovalov.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlayerDao implements GenericDao<Player, Long> {

    @Override
    public Player save(Player entity) {
        return entity;
    }

    @Override
    public Optional<Player> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Player> findByName(String name) {
        return Optional.of(new Player(name));
    }

    @Override
    public void update(Player entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Player> findAll() {
        return null;
    }

}
