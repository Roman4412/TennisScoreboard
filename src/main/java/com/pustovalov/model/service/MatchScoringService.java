package com.pustovalov.model.service;

import com.pustovalov.model.pojo.MatchScore;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MatchScoringService {

    private static volatile MatchScoringService instance;
    private final OngoingMatchService ongoingMatchService;

    public void countPoint(Long playerId, UUID matchUuid) {
        MatchScore score = ongoingMatchService.get(matchUuid).getScore();
        score.countPoint(playerId);
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

    private MatchScoringService(OngoingMatchService ongoingMatchService) {
        this.ongoingMatchService = ongoingMatchService;
    }

}
