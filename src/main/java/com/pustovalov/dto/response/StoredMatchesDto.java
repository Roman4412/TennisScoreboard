package com.pustovalov.dto.response;

import com.pustovalov.entity.Match;
import java.util.List;

public record StoredMatchesDto(
    List<Match> matches,
    String filterByPlayerName,
    int totalPages,
    int currentPage,
    boolean isLast) {}
