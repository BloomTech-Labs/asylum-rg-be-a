package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AsylumCaseServiceImpl implements AsylumCaseService {

	private AsylumCaseRepository asylumCaseRepository;
	private AsylumCaseMapper asylumCaseMapper;

	@Override
	@Cacheable("asylumCaseCache")
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases() {
		return asylumCaseMapper.entitiesToResponseDto(asylumCaseRepository.findAll());
	}

	@Override
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto) {
		return null;
	}

}
