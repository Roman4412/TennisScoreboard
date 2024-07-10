package com.pustovalov.model.entity;

import com.pustovalov.model.pojo.MatchScore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

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
    private MatchScore score;

    public Match(Player playerOne, Player playerTwo, Player winner, UUID externalId, MatchScore score) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.winner = winner;
        this.externalId = externalId;
        this.score = score;
    }

}