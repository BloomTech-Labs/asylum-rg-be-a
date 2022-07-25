package com.bloomtech.asylumrgbea.mappers;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.CaseResponseDto;
import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import org.mapstruct.Mapper;

/**
 * An interface use to convert various object and collects of objects such as RequestDtos, Entities, ResponseDtos, and Iterables.
 */
@Mapper(uses = {AsylumCaseMapper.class}, componentModel = "spring")
public interface AsylumCaseMapper {

    AsylumCase requestToEntity(CasesRequestDto casesRequestDto);

    CaseResponseDto entityToResponseDto(AsylumCase asylumCase);

    Iterable<CasesRequestDto> entitiesToResponseDtos(Iterable<AsylumCase> asylumCases);

    Iterable<CaseResponseDto> pagesToResponseDtos(Page<AsylumCase> asylumCases);

    PageResponseDto pageToResponseDto(Page page);

    PageResponseDto pageDataAndPageToResponseDto(int totalPages, Iterable<CaseResponseDto> page);
}
