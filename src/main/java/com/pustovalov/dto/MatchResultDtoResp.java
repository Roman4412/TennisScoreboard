package com.pustovalov.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class MatchResultDtoResp {

    private UUID matchId;

    private String playerOne;

    private List<String> playerOneSetPts;

    private List<String> playerOneMatchPts;

    private String playerTwo;

    private List<String> playerTwoSetPts;

    private List<String> playerTwoMatchPts;
}
