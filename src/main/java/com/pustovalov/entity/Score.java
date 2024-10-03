package com.pustovalov.entity;

import com.pustovalov.strategy.GameScoringStrategy;
import com.pustovalov.strategy.ScoringStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Score {

    private Map<Long, List<Point>> current;

    private Map<Long, List<Point>> result;

    private Match match;

    private ScoringStrategy scoringStrategy;

    public Score(Long playerOneId, Long playerTwoId) {
        current = new ConcurrentHashMap<>();
        result = new ConcurrentHashMap<>();

        List<Point> init = new ArrayList<>();
        init.add(new Point(PointUnits.GAME));
        init.add(new Point(PointUnits.SET));
        init.add(new Point(PointUnits.MATCH));
        init.add(new Point(PointUnits.TIEBREAK));

        current.put(playerOneId, new ArrayList<>(init));
        current.put(playerTwoId, new ArrayList<>(init));
        result.put(playerOneId, new ArrayList<>());
        result.put(playerTwoId, new ArrayList<>());
        scoringStrategy = new GameScoringStrategy(this);
    }

    public Point getPoint(Long playerId, PointUnits type) {
        List<Point> result = current.get(playerId).stream().filter(e -> e.getType().equals(type)).toList();

        return result.get(result.size() - 1);
    }

    public List<String> getResultScore(Long playerId, PointUnits type) {
        return result.get(playerId).stream().filter(e -> e.getType().equals(type)).map(Point::getValue).toList();
    }

    public void addPoint(Long playerId, String value, PointUnits type) {
        Point point = null;

        if (type == PointUnits.GAME) {
            point = new Point(value, PointUnits.GAME);
        } else if (type == PointUnits.SET) {
            point = new Point(value, PointUnits.SET);
        } else if (type == PointUnits.MATCH) {
            point = new Point(value, PointUnits.MATCH);
        } else if (type == PointUnits.TIEBREAK) {
            point = new Point(value, PointUnits.TIEBREAK);
        }
        current.get(playerId).add(point);
    }

    public void resetScore(PointUnits type) {
        addPoint(match.getPlayerOne().getId(), "0", type);
        addPoint(match.getPlayerTwo().getId(), "0", type);
    }

    public void saveScore(PointUnits type) {
        Long playerOneId = match.getPlayerOne().getId();
        Long playerTwoId = match.getPlayerTwo().getId();

        result.get(playerOneId).add(getPoint(playerOneId, type));
        result.get(playerTwoId).add(getPoint(playerTwoId, type));
    }

    public void changeStrategy(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }
}
