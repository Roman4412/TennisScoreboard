package com.pustovalov.strategy;

import com.pustovalov.entity.Score;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ScoringStrategy {

    protected final Score score;

    public ScoringStrategy(Score score) {
        this.score = score;
    }

    public abstract void count(Long playerId);
}
