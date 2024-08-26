package com.pustovalov.dto.response;

import com.pustovalov.entity.Match;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoredMatchesDto {

  private List<Match> matches;

  private String filterByPlayerName;

  private int totalPages;

  private int currentPage;
}
