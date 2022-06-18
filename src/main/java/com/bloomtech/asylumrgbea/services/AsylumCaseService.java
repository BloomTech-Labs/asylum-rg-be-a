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
<<<<<<< HEAD
	Iterable<AsylumCaseResponseDto> getAllAsylumCases();
=======

	ResponseEntity<Iterable<AsylumCaseResponseDto>> getAllAsylumCases();

	Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto);


>>>>>>> 096bc7212bd0f8962c18f2c37914d024361ea1c8
}
