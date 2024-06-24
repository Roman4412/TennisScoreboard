package com.pustovalov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "MATCHES", uniqueConstraints =
@UniqueConstraint(columnNames = {"PLAYER_ONE", "PLAYER_TWO"}))
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ONE")
    private Player playerOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_TWO")
    private Player playerTwo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WINNER")
    private Player winner;

    @Transient
    private UUID externalId;

    @Transient
    private GameScore score;

    public Match(UUID externalId, Player playerOne, Player playerTwo, GameScore score) {
        this.externalId = externalId;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.score = score;
    }

}