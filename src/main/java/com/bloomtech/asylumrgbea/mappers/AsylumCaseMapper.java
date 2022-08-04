package com.bloomtech.asylumrgbea.mappers;

import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.models.AsylumCaseModel;
import com.bloomtech.asylumrgbea.models.CasesQueryParameterDto;
import com.bloomtech.asylumrgbea.models.Page;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import org.mapstruct.Mapper;

/**
 * An interface used to convert various object and collects of objects such as RequestDtos,
 * Entities, ResponseDtos, and Iterables.
 */
@Mapper(uses = {AsylumCaseMapper.class}, componentModel = "spring")
public interface AsylumCaseMapper {

    AsylumCase queryParametersToEntity(CasesQueryParameterDto casesQueryParameterDto);

    AsylumCaseModel entityToModel(AsylumCase asylumCase);

    Iterable<CasesQueryParameterDto> entitiesToQueryParameters(Iterable<AsylumCase> asylumCases);

    Iterable<AsylumCaseModel> pagesToModels(Page<AsylumCase> asylumCases);

    PageResponseDto pageToResponseDto(Page page);

    PageResponseDto pageDataAndPageToResponseDto(int totalPages, Iterable<AsylumCaseModel> page);
}
