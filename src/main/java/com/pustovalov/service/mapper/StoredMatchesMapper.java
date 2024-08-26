package com.pustovalov.service.mapper;

import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper
public interface StoredMatchesMapper {
    StoredMatchesDto toStoredMatchesDto(List<Match> matches, int totalPages, int currentPage);
    @Mapping(source = "name", target = "filterByPlayerName")
    StoredMatchesDto toStoredMatchesDto(List<Match> matches, String name, int totalPages,
                                        int currentPage);
}
