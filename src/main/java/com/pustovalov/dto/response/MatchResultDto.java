package com.pustovalov.dto.response;

import com.pustovalov.entity.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MatchResultDto {

    private UUID matchId;

    private Player playerOne;

    private List<String> playerOneSetPts;

    private String playerOneMatchPts;

    private Player playerTwo;

    private List<String> playerTwoSetPts;

    private String playerTwoMatchPts;

}