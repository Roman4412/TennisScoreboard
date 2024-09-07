package com.pustovalov.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Entity
@Table(name = "matches")
@Check(constraints = "player_one_id <> player_two_id")
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

  @Setter
  private UUID externalId;

  @Transient private boolean finished;

  @Getter @Transient private Score score;

  public Match(Player playerOne, Player playerTwo, UUID externalId) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.externalId = externalId;
  }

  public Match() {}

  public void finish() {
    finished = true;
  }

  public void setScore(Score score) {
    if (this.score == null) {
      this.score = score;
      score.setMatch(this);
    }
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
