package com.pustovalov.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MatchResultsResponse {

    String playerOneName;

    String playerOneSetPts;

    String playerOneMatchPts;

    String playerTwoName;

    String playerTwoSetPts;

    String playerTwoMatchPts;

}
