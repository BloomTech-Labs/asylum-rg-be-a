package com.bloomtech.asylumrgbea.services;

import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.entities.AsylumCase;
import com.bloomtech.asylumrgbea.mappers.AsylumCaseMapper;
import com.bloomtech.asylumrgbea.models.CasesRequestDto;
import com.bloomtech.asylumrgbea.repositories.AsylumCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsylumCaseService {

	private final AsylumCaseRepository asylumCaseRepository;

	private final AsylumCaseMapper asylumCaseMapper;

	/**
	 * Gets all entities from repository and returns them out of the program.
	 * @return This is an Iterable of AsylumCase objects.
	 */
	@Cacheable("asylum_case_cache")
	public Iterable<CasesRequestDto> getAllAsylumCases() {

		Iterable<AsylumCase> caseIterable = asylumCaseRepository.findAll();
		validateIterableIsNotEmpty(caseIterable);

		return asylumCaseMapper.entitiesToResponseDtos(caseIterable);
	}

	/**
	 * Gets a page of entities from repository and returns them out of the program.
	 * @param casesRequestDto Contains the page number as a field.
	 * @return This is a limited Iterable of AsylumCase objects.
	 */


	/**
	 * Checks if an Iterable has at least 1 element and throws an exception if not so.
	 * @param caseIterable An empty or non-empty Iterable of AsylumCase objects.
	 * @throws AsylumCaseNotFoundException if the Iterable is empty this exception is thrown.
	 */
	private void validateIterableIsNotEmpty(Iterable<AsylumCase> caseIterable) throws AsylumCaseNotFoundException {

		if (!caseIterable.iterator().hasNext())
			throw new AsylumCaseNotFoundException("ERROR: No cases were found...");
	}

	/**
	 * Checks if a CasesRequestDto object contains a null field and throws an exception if so.
	 * @param casesRequestDto package and a request Dto.
	 * @throws BadRequestException The exception thrown if triggered.
	 */
	private void validateRequestDto(CasesRequestDto casesRequestDto) throws BadRequestException {

		if (casesRequestDto.getNumberOfItemsInPage() == null)
			throw new BadRequestException("ERROR: The field number of items in a page cannot be null...");
	}
}
