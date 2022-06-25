package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;

public interface AsylumCaseService {

	Iterable<AsylumCaseResponseDto> getAllAsylumCases();

	PageResponseDto getPageOfAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto);
}
