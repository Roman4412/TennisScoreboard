package com.pustovalov.state;

import com.pustovalov.entity.Score;

public abstract class MatchScoringState {
    protected static final String DEFAULT_PTS = "0";
    protected static final String FIFTEEN_PTS = "15";
    protected static final String THIRTY_PTS = "30";
    protected static final String FORTY_PTS = "40";
    protected static final String ADVANTAGE = "AD";
    protected static final int GAME_PTS_FOR_WIN = 6;
    protected static final int TIEBREAK_PTS_FOR_WIN = 7;
    protected static final int POINT_DIFFERENCE_FOR_WIN = 2;


    protected Score score;
    public abstract void count(Long playerId);

    public MatchScoringState(Score score) {
        this.score = score;
    }

}
