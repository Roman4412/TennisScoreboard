package com.pustovalov.service.mapper;

import com.pustovalov.dto.request.CreateMatchDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface CreateMatchMapper {
    @Mapping(source = "player-one-name", target = "playerOneName")
    @Mapping(source = "player-two-name", target = "playerTwoName")
    CreateMatchDto toCreateMatchDto(Map<String, String> params);
}
