package com.pustovalov.strategy;

public abstract class ScoringStrategy {

    protected Score score;
    public abstract void count(Long playerId);

    public ScoringStrategy(Score score) {
        this.score = score;
    }

}
