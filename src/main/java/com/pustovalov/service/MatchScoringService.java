package com.pustovalov.service;

import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.entity.Match;
import com.pustovalov.service.mapper.MatchMapper;
import com.pustovalov.strategy.Score;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatchScoringService {

    private static volatile MatchScoringService instance;

    private final OngoingMatchService ongoingMatchService;

    private final MatchMapper mapper;

    private MatchScoringService(OngoingMatchService ongoingMatchService) {
        this.ongoingMatchService = ongoingMatchService;
        mapper = MatchMapper.INSTANCE;
    }

    public static MatchScoringService getInstance() {
        if (instance == null) {
            synchronized(MatchScoringService.class) {
                if (instance == null) {
                    instance = new MatchScoringService(OngoingMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    public MatchScoreDto countPoint(Long playerId, UUID uuid) {
        Match match = ongoingMatchService.getMatch(uuid);
        Score score = match.getScore();
        score.count(playerId);

        return mapper.toMatchScoreDto(match);
    }

}
