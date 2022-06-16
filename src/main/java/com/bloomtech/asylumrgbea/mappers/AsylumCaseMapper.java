package com.bloomtech.asylumrgbea.mappers;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {AsylumCaseMapper.class}, componentModel = "spring")
public interface AsylumCaseMapper {

    AsylumCase requestToEntity(AsylumCaseRequestDto asylumCaseRequestDto);

    AsylumCaseResponseDto entityToResponseDto(AsylumCase asylumCase);

    Iterable<AsylumCaseResponseDto> entitiesToResponseDto(Iterable<AsylumCase> asylumCases);
}
