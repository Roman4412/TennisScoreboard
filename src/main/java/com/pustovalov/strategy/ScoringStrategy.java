package com.pustovalov.strategy;

public abstract class ScoringStrategy {

    protected Score score;

    public ScoringStrategy(Score score) {
        this.score = score;
    }

    public abstract void count(Long playerId);

}
