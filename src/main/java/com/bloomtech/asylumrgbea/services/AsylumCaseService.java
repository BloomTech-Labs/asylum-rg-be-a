package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import org.springframework.http.ResponseEntity;

/*
Create an interface method called getAllAsylumCases
that takes in AsylumCaseRequestDto asylumCaseRequestDto as a parameter and returns an Iterable<AsylumCaseResponseDto>.
This method should have no functionality but defined the behavior.
 */
public interface AsylumCaseService {

	ResponseEntity<Iterable<AsylumCaseResponseDto>> getAllAsylumCases();

	Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto);


}
