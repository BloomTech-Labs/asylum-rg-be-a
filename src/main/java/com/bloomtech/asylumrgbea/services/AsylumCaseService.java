package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;

public interface AsylumCaseService {

	Iterable<AsylumCaseResponseDto> getAllAsylumCases();

	Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto);
}
