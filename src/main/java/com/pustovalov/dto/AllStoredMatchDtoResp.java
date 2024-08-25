package com.pustovalov.dto;

import com.pustovalov.entity.Match;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AllStoredMatchDtoResp {

    private List<Match> matches;

    private String filterByPlayerName;

    private int totalPages;

    private int currentPage;

}



