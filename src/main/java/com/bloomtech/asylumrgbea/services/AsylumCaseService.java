package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import org.springframework.http.ResponseEntity;

public interface AsylumCaseService {
	ResponseEntity<Iterable<AsylumCaseResponseDto>> getAllAsylumCases();
}
