package com.pustovalov.dto.response;

import com.pustovalov.entity.Player;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

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
