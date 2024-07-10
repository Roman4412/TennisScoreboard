package com.pustovalov.model.service;

import com.pustovalov.model.pojo.MatchScore;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ScoringService {

    private static volatile ScoringService instance;
    private final CurrentMatchService currentMatchService;

    public void countPoint(Long playerId, UUID matchUuid) {
        MatchScore score = currentMatchService.get(matchUuid).getScore();
        score.countPoint(playerId);
    }
    public static ScoringService getInstance() {
        if (instance == null) {
            synchronized(ScoringService.class) {
                if (instance == null) {
                    instance = new ScoringService(CurrentMatchService.getInstance());
                }
            }
        }
        return instance;
    }

    private ScoringService(CurrentMatchService currentMatchService) {
        this.currentMatchService = currentMatchService;
    }

}
