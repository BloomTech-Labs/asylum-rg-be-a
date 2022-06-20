package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.AsylumCaseRequestDto;
import com.bloomtech.asylumrgbea.models.AsylumCaseResponseDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsylumCaseServiceImpl implements AsylumCaseService {

	private final AsylumCaseRepository asylumCaseRepository;

	private final AsylumCaseMapper asylumCaseMapper;

	@Override
	@Cacheable("asylum_case_cache")
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases() {
		return asylumCaseMapper.entitiesToResponseDtos(asylumCaseRepository.findAll());
	}

	@Override
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto) {
		return null;
	}

}
