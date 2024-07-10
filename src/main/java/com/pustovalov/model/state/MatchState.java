package com.pustovalov.model.state;

import com.pustovalov.model.pojo.MatchScore;

public abstract class MatchState {
    protected static final String DEFAULT_PTS = "0";
    protected static final String FIFTEEN_PTS = "15";
    protected static final String THIRTY_PTS = "30";
    protected static final String FORTY_PTS = "40";
    protected static final String ADVANTAGE = "AD";
    protected static final int GAME_PTS_FOR_WIN = 6;
    protected static final int TIEBREAK_PTS_FOR_WIN = 7;
    protected static final int POINT_DIFFERENCE_FOR_WIN = 2;

    protected static final String POINT = "Point";
    protected static final String GAME = "Game";
    protected static final String SET = "Set";
    protected static final String TIEBREAK = "Tiebreak";

    protected MatchScore matchScore;
    public abstract void count(Long playerId);

    public MatchState(MatchScore matchScore) {
        this.matchScore = matchScore;
    }

}
