package com.pustovalov.dto.response;

import com.pustovalov.entity.Match;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AllStoredMatchDto {

    private List<Match> matches;

    private String filterByPlayerName;

    private int totalPages;

    private int currentPage;

}



