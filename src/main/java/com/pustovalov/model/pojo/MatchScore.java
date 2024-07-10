package com.pustovalov.model.pojo;

import com.pustovalov.model.state.MatchState;
import com.pustovalov.model.state.Point;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MatchScore {

    private static final String DEFAULT_PTS = "0";
    private static final String POINT = "Point";
    private static final String GAME = "Game";
    private static final String SET = "Set";
    private static final String TIEBREAK = "Tiebreak";

    private final Long playerOneId;
    private final Long playerTwoId;
    private final Map<Long, Map<String, String>> score;
    private MatchState matchState = new Point(this);

    public void countPoint(Long playerId) {
        matchState.count(playerId);
    }

    public String getPlayersScore(Long playerId, String scoreUnit) {
        if (score.containsKey(playerId)) {
            return score.get(playerId).get(scoreUnit);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    public void setScore(Long playerId, String scoreUnit, String value) {
        if (score.containsKey(playerId)) {
            Map<String, String> playersScore = score.get(playerId);
            playersScore.put(scoreUnit, value);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    public void resetScore(String scoreUnit) {
        setScore(playerOneId,scoreUnit,DEFAULT_PTS);
        setScore(playerTwoId,scoreUnit,DEFAULT_PTS);
    }

    public Long getOpponentId(Long playerId) {
        if (playerOneId.equals(playerId)) {
            return playerTwoId;
        } else {
            return playerOneId;
        }
    }

    private void initScore() {
        Map<String, String> playerOneScore = new HashMap<>();
        playerOneScore.put(POINT, DEFAULT_PTS);
        playerOneScore.put(GAME, DEFAULT_PTS);
        playerOneScore.put(SET, DEFAULT_PTS);
        playerOneScore.put(TIEBREAK, DEFAULT_PTS);
        score.put(playerOneId, playerOneScore);

        Map<String, String> playerTwoScore = new HashMap<>();
        playerTwoScore.put(POINT, DEFAULT_PTS);
        playerTwoScore.put(GAME, DEFAULT_PTS);
        playerTwoScore.put(SET, DEFAULT_PTS);
        playerTwoScore.put(TIEBREAK, DEFAULT_PTS);
        score.put(playerTwoId, playerTwoScore);
    }

    public MatchScore(Long playerOneId, Long playerTwoId) {
        score = new HashMap<>();
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
        initScore();
    }

    public void changeState(MatchState state) {
        this.matchState = state;
    }

}
