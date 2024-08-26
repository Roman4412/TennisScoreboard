package com.pustovalov.entity;

import com.pustovalov.strategy.Score;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(
    name = "matches",
    uniqueConstraints = @UniqueConstraint(columnNames = {"player_one_id", "player_two_id"}))
public class Match {

  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_one_id")
  private Player playerOne;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_two_id")
  private Player playerTwo;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "winner_id")
  private Player Winner;

  @Setter @Transient private UUID externalId;

  @Transient private boolean isFinished;

  @Transient private Score score;

  public Match(Player playerOne, Player playerTwo, UUID externalId) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.externalId = externalId;
  }

  public Match() {}

  public void finish() {
    isFinished = true;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setScore(Score score) {
    if (score == null) {
      throw new NullPointerException("Can't set a null score");
    }
    if (score.getMatch() != null) {
      throw new IllegalStateException("The match has already been set for this relationship");
    }
    this.score = score;
    score.setMatch(this);
  }

  public Long getOpponentId(Long playerId) {
    Long playerOneId = playerOne.getId();
    Long playerTwoId = playerTwo.getId();

    if (playerOneId.equals(playerId)) {
      return playerTwoId;
    } else {
      return playerOneId;
    }
  }
}
