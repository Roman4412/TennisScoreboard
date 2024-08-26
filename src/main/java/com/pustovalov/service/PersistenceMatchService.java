package com.pustovalov.service;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.dto.response.AllStoredMatchDto;
import com.pustovalov.dto.response.MatchResultDto;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.service.mapper.MatchMapper;
import com.pustovalov.strategy.Score;

import java.util.List;
import java.util.UUID;

public class PersistenceMatchService {
    public static final int LIMIT = 10;
    private static volatile PersistenceMatchService instance;
    private final HibernateMatchDao hibernateMatchDao;
    private final OngoingMatchService ongoingMatchService;

    private final MatchMapper mapper;

    public PersistenceMatchService(HibernateMatchDao hibernateMatchDao,
                                   OngoingMatchService ongoingMatchService) {
        this.hibernateMatchDao = hibernateMatchDao;
        this.ongoingMatchService = ongoingMatchService;
        this.mapper = MatchMapper.INSTANCE;
    }

    public static PersistenceMatchService getInstance() {
        if (instance == null) {
            synchronized(MatchScoringService.class) {
                if (instance == null) {
                    instance = new PersistenceMatchService(HibernateMatchDao.getInstance(),
                            OngoingMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    public MatchResultDto save(UUID matchId) {
        Match match = ongoingMatchService.getMatch(matchId);
        hibernateMatchDao.save(match);
        ongoingMatchService.delete(matchId);

        Player playerOne = match.getPlayerOne();
        Player playerTwo = match.getPlayerTwo();
        Score score = match.getScore();
        System.out.println(mapper.toMatchResultDto(match));
        return mapper.toMatchResultDto(match);
       /* return MatchResultDto.builder()
                .matchId(matchId)
                .playerOne(playerOne)
                .playerOneSetPts(score.getResultPoints(playerOne.getId(), ScoreUnits.SET))
                .playerOneMatchPts(score.getResultPoints(playerOne.getId(), ScoreUnits.MATCH))
                .playerTwo(playerTwo)
                .playerTwoSetPts(score.getResultPoints(playerTwo.getId(), ScoreUnits.SET))
                .playerTwoMatchPts(score.getResultPoints(playerTwo.getId(), ScoreUnits.MATCH))
                .build();*/
    }

    public AllStoredMatchDto findAll(int page) {
        int totalPages = getTotalPages();
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findAll(offset, LIMIT);

        return AllStoredMatchDto.builder().totalPages(totalPages).matches(matches).currentPage(
                page).build();
    }

    public AllStoredMatchDto findAll(int page, String name) {
        int totalPages = getTotalPages(name);
        int offset = page * LIMIT;
        List<Match> matches = hibernateMatchDao.findByPlayerName(offset, LIMIT, name);

        return AllStoredMatchDto.builder()
                .totalPages(totalPages)
                .matches(matches)
                .filterByPlayerName(name)
                .currentPage(page)
                .build();
    }

    private int getTotalPages() {
        Long total = hibernateMatchDao.getRowsAmount();
        return (int) (total / LIMIT);
    }

    private int getTotalPages(String name) {
        Long total = hibernateMatchDao.getRowsAmount(name);
        return (int) (total / LIMIT);
    }

}