package com.pustovalov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}