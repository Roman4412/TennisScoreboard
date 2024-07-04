package com.pustovalov.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameScore {

    private int playerOnePoint;
    private int playerOneGame;
    private int playerOneSet;
    private int playerOneTieBreak;

    private int playerTwoPoint;
    private int playerTwoGame;
    private int playerTwoSet;
    private int playerTwoTieBreak;

}
