package com.pustovalov.entity;

import com.pustovalov.enums.ScoreUnits;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreContainer {
  private final List<String> game;

  private final List<String> tiebreak;

  private final List<String> set;

  private final List<String> match;

  public ScoreContainer(String initValue) {
    game = new ArrayList<>();
    tiebreak = new ArrayList<>();
    set = new ArrayList<>();
    match = new ArrayList<>();

    game.add(initValue);
    tiebreak.add(initValue);
    set.add(initValue);
    match.add(initValue);
  }

  public ScoreContainer() {
    game = new ArrayList<>();
    tiebreak = new ArrayList<>();
    set = new ArrayList<>();
    match = new ArrayList<>();
  }

  public String getPoint(ScoreUnits units) {
    if (units == null) {
      throw new IllegalArgumentException("Units cannot be null");
    }
    return switch (units) {
      case GAME -> game.get(game.size() - 1);
      case TIEBREAK -> tiebreak.get(tiebreak.size() - 1);
      case SET -> set.get(set.size() - 1);
      case MATCH -> match.get(match.size() - 1);
    };
  }

  public List<String> getAllPoints(ScoreUnits units) {
    if (units == null) {
      throw new IllegalArgumentException("Units cannot be null");
    }
    return switch (units) {
      case GAME -> Collections.unmodifiableList(game);
      case TIEBREAK -> Collections.unmodifiableList(tiebreak);
      case SET -> Collections.unmodifiableList(set);
      case MATCH -> Collections.unmodifiableList(match);
    };
  }

  public void addPoint(ScoreUnits units, String value) {
    if (units == null) {
      throw new IllegalArgumentException("Units cannot be null");
    }
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Value cannot be null");
    }
    switch (units) {
      case GAME -> game.add(value);
      case TIEBREAK -> tiebreak.add(value);
      case SET -> set.add(value);
      case MATCH -> match.add(value);
    }
  }
}
