package com.bloomtech.asylumrgbea.services.impl;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
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

	/**
	 * Gets all entities from repository and returns them out of the program.
	 * @return This is an Iterable of AsylumCase objects.
	 */
	@Override
	@Cacheable("asylum_case_cache")
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases() {
		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToResponseDtos(caseIterable);
	}

	/**
	 * Gets a page of entities from repository and returns them out of the program.
	 * @param asylumCaseRequestDto Contains the page number as a field.
	 * @return This is a limited Iterable of AsylumCase objects.
	 */
	@Override
	public Iterable<AsylumCaseResponseDto> getAllAsylumCases(AsylumCaseRequestDto asylumCaseRequestDto) {
		validateRequestDto(asylumCaseRequestDto);

		return null;
	}

	/**
	 * Checks if an Iterable has at least 1 element and throws an exception if not so.
	 * @param caseIterable An empty or non-empty Iterable of AsylumCase objects.
	 * @throws AsylumCaseNotFoundException if the Iterable is empty this exception is thrown.
	 */
	private void validateIterableIsNotEmpty(Iterable<AsylumCase> caseIterable) throws AsylumCaseNotFoundException {
		if (!caseIterable.iterator().hasNext()) throw new AsylumCaseNotFoundException("ERROR: No cases were found...");
	}

	/**
	 * Checks if a AsylumCaseRequestDto object contains a null field and throws an exception if so.
	 * @param asylumCaseRequestDto
	 * @throws BadRequestException
	 */
	private void validateRequestDto(AsylumCaseRequestDto asylumCaseRequestDto) throws BadRequestException {
		// TODO: 6/21/22  Replace the if statement conditional with getters null checks.
		if (asylumCaseRequestDto == null) throw new BadRequestException("ERROR: Enter message here...");
	}

}
