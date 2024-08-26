package com.pustovalov.dao;

import com.pustovalov.entity.Match;
import java.util.List;

public interface MatchDao {

    Match save(Match match);

    List<Match> findAll(int offset, int limit);

    List<Match> findByPlayerName(int offset, int limit, String name);

    Long getRowsAmount();

    Long getRowsAmount(String name);

}
