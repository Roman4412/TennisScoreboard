package com.pustovalov.dao;

import com.pustovalov.model.entity.Match;

import java.util.Optional;

public interface MatchDao<I> {

    Match save(Match match);

    Optional<Match> findById(I id);

    void delete(I id);

}
