package com.pustovalov.dto.response;

import com.pustovalov.entity.Player;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MatchScoreDto {

    private String uuid;

    private Player playerOne;

    private String playerOneGamePts;

    private String playerOneSetPts;

    private String playerOneMatchPts;

    private String playerOneTiebreakPts;

    private Player playerTwo;

    private String playerTwoGamePts;

    private String playerTwoSetPts;

    private String playerTwoMatchPts;

    private String playerTwoTiebreakPts;

    private boolean isFinished;

}
