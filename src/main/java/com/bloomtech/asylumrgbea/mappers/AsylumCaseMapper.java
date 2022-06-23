package com.bloomtech.asylumrgbea.mappers;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(uses = {AsylumCaseMapper.class}, componentModel = "spring")
public interface AsylumCaseMapper {
    AsylumCase requestToEntity(AsylumCaseRequestDto asylumCaseRequestDto);
    AsylumCaseResponseDto entityToResponseDto(AsylumCase asylumCase);
    Iterable<AsylumCaseResponseDto> entitiesToResponseDtos(Iterable<AsylumCase> asylumCases);
    Iterable<AsylumCaseResponseDto> pageToResponseDtos(Page<AsylumCase> asylumCases);
    PageResponseDto pageDataAndPageToResponseDto(int totalPages, Iterable<AsylumCaseResponseDto> page);
}
