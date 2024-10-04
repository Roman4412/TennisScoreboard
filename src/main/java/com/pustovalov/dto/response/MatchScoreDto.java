package com.pustovalov.dto.response;

import com.pustovalov.entity.Player;
import com.pustovalov.entity.Score;

public record MatchScoreDto(String uuid, Player playerOne, Score score, Player playerTwo, boolean finished) {

}