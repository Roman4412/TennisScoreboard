package com.pustovalov.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameScore {

    private int playerOneGamePoints;
    private int playerOneSetPoints;
    private int playerOneTieBreakPoints;
    private int playerTwoGamePoints;
    private int playerTwoSetPoints;
    private int playerTwoTieBreakPoints;

}
