package com.pustovalov.entity;

import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.state.MatchScoringState;
import com.pustovalov.state.GameScoring;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter

@Entity
@Table(name = "SCORE")
public class Score {

    @Transient
    private static final String DEFAULT_PTS = "0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(mappedBy = "score")
    private Match match;

    @Setter
    @Transient
    private Map<Long, Map<ScoreUnits, String>> currentScore = new ConcurrentHashMap<>();

    @ElementCollection
    @OrderColumn(name = "order_index")
    @CollectionTable(name = "PLAYER_ONE_SET_SCORE",
                     joinColumns = @JoinColumn(name = "ID"))
    private List<String> playerOneSetScore;

    @ElementCollection
    @OrderColumn(name = "order_index")
    @CollectionTable(name = "PLAYER_TWO_SET_SCORE",
                     joinColumns = @JoinColumn(name = "ID"))
    private List<String> playerTwoSetScore;

    @Setter
    @Transient
    private MatchScoringState matchScoringState = new GameScoring(this);

    public String getPlayerScore(Long playerId, ScoreUnits scoreUnit) {
        if (currentScore.containsKey(playerId)) {
            return currentScore.get(playerId).get(scoreUnit);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    public void setCurrentScore(Long playerId, ScoreUnits scoreUnit, String value) {
        if (currentScore.containsKey(playerId)) {
            Map<ScoreUnits, String> playersScore = currentScore.get(playerId);
            playersScore.put(scoreUnit, value);
        } else {
            throw new RuntimeException(String.format("there is no info for a player named %S", playerId));
        }
    }

    public void resetScore(ScoreUnits scoreUnit) {
        setCurrentScore(match.getPlayerOne().getId(), scoreUnit, DEFAULT_PTS);
        setCurrentScore(match.getPlayerTwo().getId(), scoreUnit, DEFAULT_PTS);
    }

    public void saveScore() {
        Long playerOneId = match.getPlayerOne().getId();
        Long playerTwoId = match.getPlayerTwo().getId();

        playerOneSetScore.add(getPlayerScore(playerOneId, ScoreUnits.GAME));
        playerTwoSetScore.add(getPlayerScore(playerTwoId, ScoreUnits.GAME));

    }

    public Long getOpponentId(Long playerId) {
        Long playerOneId = match.getPlayerOne().getId();
        Long playerTwoId = match.getPlayerTwo().getId();

        if (playerOneId.equals(playerId)) {
            return playerTwoId;
        } else {
            return playerOneId;
        }
    }

    public void changeState(MatchScoringState state) {
        this.matchScoringState = state;
    }

    private void initScore() {
        Long playerOneId = match.getPlayerOne().getId();
        Long playerTwoId = match.getPlayerTwo().getId();

        Map<ScoreUnits, String> playerOneScore = new ConcurrentHashMap<>();
        playerOneScore.put(ScoreUnits.POINT, DEFAULT_PTS);
        playerOneScore.put(ScoreUnits.GAME, DEFAULT_PTS);
        playerOneScore.put(ScoreUnits.SET, DEFAULT_PTS);
        playerOneScore.put(ScoreUnits.TIEBREAK, DEFAULT_PTS);

        Map<ScoreUnits, String> playerTwoScore = new ConcurrentHashMap<>(playerOneScore);

        currentScore.put(playerOneId, playerOneScore);
        currentScore.put(playerTwoId, playerTwoScore);

        playerOneSetScore = new ArrayList<>();
        playerTwoSetScore = new ArrayList<>();
    }

    public void setMatch(Match match) {
        this.match = match;
        initScore();
    }

    public Score() {

    }

}
