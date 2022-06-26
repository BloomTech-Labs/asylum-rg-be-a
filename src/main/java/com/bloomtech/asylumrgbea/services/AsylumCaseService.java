package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;

public interface AsylumCaseService {

	PageResponseDto getPageOfAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto);

	Iterable<AsylumCaseResponseDto> getAllAsylumCases();

}
