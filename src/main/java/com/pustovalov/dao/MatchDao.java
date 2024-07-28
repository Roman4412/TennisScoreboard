package com.pustovalov.dao;

import com.pustovalov.entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchDao<I> {

    Match save(Match match);

    Optional<Match> findById(I id);

    void delete(I id);

    List<Match> findAll(int offset, int limit);

}
