package com.pustovalov.entity;

import com.pustovalov.enums.ScoreUnits;
import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
public class Points {

    public static final String DEFAULT_VALUE = "0";

    private String game;

    private String tiebreak;

    private String set;

    private String match;

    public Points() {
        game = DEFAULT_VALUE;
        tiebreak = DEFAULT_VALUE;
        set = DEFAULT_VALUE;
        match = DEFAULT_VALUE;
    }

    public String getPoint(ScoreUnits units) {
        if (units == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        return switch(units) {
            case GAME -> game;
            case TIEBREAK -> tiebreak;
            case SET -> set;
            case MATCH -> match;
        };
    }

    public void setPoint(ScoreUnits units, String value) {
        if (units == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        switch(units) {
            case GAME -> setGame(value);
            case TIEBREAK -> setTiebreak(value);
            case SET -> setSet(value);
            case MATCH -> setMatch(value);
        }
    }

    @Override
    public String toString() {
        return "Points{" + "game=" + game + ", tiebreak=" + tiebreak + ", set=" + set + ", match=" + match + '}';
    }

}