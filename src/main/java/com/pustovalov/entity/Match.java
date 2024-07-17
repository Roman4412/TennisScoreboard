package com.pustovalov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter

@Entity
@Table(name = "MATCHES", uniqueConstraints =
@UniqueConstraint(columnNames = {"PLAYER_ONE", "PLAYER_TWO"}))
public class Match {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ONE")
    private Player playerOne;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_TWO")
    private Player playerTwo;

    @Setter
    @ManyToOne
    @JoinColumn
    private Player winner;

    @Setter
    @Transient
    private UUID externalId;

    @OneToOne(cascade = CascadeType.ALL)
    private Score score;

    @Transient
    private boolean isFinished;

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


    public Match(Player playerOne, Player playerTwo, UUID externalId) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.externalId = externalId;
    }
    public Match() {

    }

}